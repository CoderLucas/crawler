package com.lujh.util;

import com.lujh.bean.AccessLog;
import com.lujh.service.AccessLogService;
import com.lujh.service.KeyService;
import com.lujh.util.enums.AccessLogStatus;
import com.lujh.util.enums.KeyValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by lujianhao on 2018/2/9.
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private AccessLogService accessLogService;
    @Autowired
    private KeyService keyService;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）调用
     * 返回true 则放行， false 则将直接跳出方法
     *
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String ip = getIpAddr(request);
        System.out.println(ip);
        String referer = request.getHeader("Referer");
        String userAgent = request.getHeader("User-Agent");
        AccessLog accessLog = new AccessLog();
        accessLog.setIp(ip);
        accessLog.setReferer(referer);
        accessLog.setUseragent(userAgent);
        accessLog.setCreatetime(new Date());

        // IP白名单验证
        List<String> ipWhiteList = keyService.getValueByKey(KeyValue.ip_whitelist.getValue());
        if (ipWhiteList.contains(ip)) {
            accessLog.setStatus(AccessLogStatus.SUCCESS.getValue());
            accessLogService.add(accessLog);
            return true;
        }

        // IP黑名单验证
        List<String> ipBlackList = keyService.getValueByKey(KeyValue.ip_blacklist.getValue());
        if (ipBlackList.contains(ip)) {
            accessLog.setStatus(AccessLogStatus.FAIL.getValue());
            accessLogService.add(accessLog);
            return false;
        }

        // IP次数验证
        String ipStatus = keyService.getValueByKey(KeyValue.ip_status.getValue()).get(0);
        if ("1".equals(ipStatus)) {
            int ipLimit = Integer.valueOf(keyService.getValueByKey(KeyValue.ip_limit.getValue()).get(0));
            int ipAccess = accessLogService.countByIP(ip, AccessLogStatus.SUCCESS, new Date(System.currentTimeMillis() - 10 * 60 * 1000), new Date());
            if (ipAccess >= ipLimit) {
                accessLog.setStatus(AccessLogStatus.FAIL.getValue());
                accessLogService.add(accessLog);
                return false;
            }
        }
        // referer验证
        Wrapper wrapper = new Wrapper(true);
        String refererStatus = keyService.getValueByKey(KeyValue.referer_status.getValue()).get(0);
        if ("1".equals(refererStatus)) {
            List<String> refererList = keyService.getValueByKey(KeyValue.referer_limit.getValue());
            refererList.forEach(string -> {
                if (StringUtils.isNotBlank(referer) && referer.toLowerCase().contains(string)) {
                    wrapper.set(false);
                    return;
                }
            });
            if (!(boolean) wrapper.get()) {
                accessLog.setStatus(AccessLogStatus.FAIL.getValue());
                accessLogService.add(accessLog);
                return false;
            }
        }
        accessLog.setStatus(AccessLogStatus.SUCCESS.getValue());
        accessLogService.add(accessLog);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 获取访问的ip地址
     *
     * @param request
     * @return
     */

    private static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
