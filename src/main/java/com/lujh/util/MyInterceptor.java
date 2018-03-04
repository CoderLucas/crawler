package com.lujh.util;

import com.lujh.bean.AccessLog;
import com.lujh.service.AccessLogService;
import com.lujh.service.KeyService;
import com.lujh.util.enums.AccessLogStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
        AccessLog accessLog = new AccessLog();
        accessLog.setIp(ip);
        accessLog.setReferer(request.getHeader("Referer"));
        accessLog.setUseragent(request.getHeader("User-Agent"));
        accessLog.setCreatetime(new Date());

        // IP次数验证
        int ipLimit = Integer.valueOf(keyService.getValueByKey("ip_limit").get(0));
        int ipAccess = accessLogService.countByIP(ip,AccessLogStatus.SUCCESS, new Date(System.currentTimeMillis() - 10 * 60 * 1000), new Date());
        if (ipAccess >= ipLimit) {
            accessLog.setStatus(AccessLogStatus.FAIL.getValue());
            accessLogService.add(accessLog);
            return false;
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
