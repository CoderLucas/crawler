package com.lujh.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lujh.bean.AccessLogListOut;
import com.lujh.service.AccessLogService;
import com.lujh.service.KeyService;
import com.lujh.util.DateUtil;
import com.lujh.util.Msg;
import com.lujh.util.SimpleObject;
import com.lujh.util.enums.AccessLogStatus;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by lujianhao on 2018/3/4.
 */
@RestController
@RequestMapping(value = "/accesslog")
public class AccessLogController {
    @Autowired
    private AccessLogService accessLogService;
    @Autowired
    private KeyService keyService;

    /**
     * 获取当日的所有IP访问记录
     *
     * @return
     */
    @GetMapping(value = "/ip/list")
    public Msg ipList(@RequestParam(value = "pn", defaultValue = "1", required = false) Integer pn,
                      @RequestParam(value = "size", defaultValue = "1000", required = false) Integer size) {
        try {

            PageHelper.startPage(pn, size);
            Date fromDate = DateUtil.getStartTime(new Date());
            Date toDate = DateUtil.getEndTime(new Date());
            List<String> ipList = accessLogService.listByIP(fromDate, toDate);
            List<AccessLogListOut> accessLogListOutList = new LinkedList<>();
            ipList.forEach(ip -> {
                AccessLogListOut accessLogListOut = new AccessLogListOut();
                accessLogListOut.setIp(ip);
                accessLogListOut.setSuccessNumber(accessLogService.countByIP(ip, AccessLogStatus.SUCCESS, fromDate, toDate));
                accessLogListOut.setFailNumber(accessLogService.countByIP(ip, AccessLogStatus.FAIL, fromDate, toDate));
                accessLogListOut.setFromTime(DateFormatUtils.format(fromDate, DateUtil.DATE_FORMAT_PATTERN_DEFAULT));
                accessLogListOut.setToTime(DateFormatUtils.format(toDate, DateUtil.DATE_FORMAT_PATTERN_DEFAULT));
                accessLogListOutList.add(accessLogListOut);
            });
            PageInfo pageInfo = new PageInfo(ipList, 5);
            pageInfo.setList(accessLogListOutList);
            return Msg.success().add("pageInfo", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    /**
     * 按小时分段
     *
     * @param ip
     * @return
     */
    @GetMapping(value = "/ip")
    public Msg ip(@RequestParam(value = "ip") String ip) {
        try {
            List<Date> dateList = DateUtil.dateList(12);
            List<AccessLogListOut> accessLogListOutList = new LinkedList<>();
            for (int i = 0; i < dateList.size() - 1; i++) {
                AccessLogListOut accessLogListOut = new AccessLogListOut();
                accessLogListOut.setIp(ip);
                accessLogListOut.setSuccessNumber(accessLogService.countByIP(ip, AccessLogStatus.SUCCESS, dateList.get(i), dateList.get(i + 1)));
                accessLogListOut.setFailNumber(accessLogService.countByIP(ip, AccessLogStatus.FAIL, dateList.get(i), dateList.get(i + 1)));
                accessLogListOut.setFromTime(DateFormatUtils.format(dateList.get(i), DateUtil.DATE_FORMAT_PATTERN_DEFAULT));
                accessLogListOut.setToTime(DateFormatUtils.format(dateList.get(i + 1), DateUtil.DATE_FORMAT_PATTERN_DEFAULT));
                accessLogListOutList.add(accessLogListOut);
            }
            return Msg.success().add("pageInfo", accessLogListOutList);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @GetMapping(value = "/referer")
    public Msg refererList(@RequestParam(value = "pn", defaultValue = "1", required = false) Integer pn,
                           @RequestParam(value = "size", defaultValue = "1000", required = false) Integer size) {
        try {
            PageHelper.startPage(pn, size);
            Date fromDate = DateUtil.getStartTime(new Date());
            Date toDate = DateUtil.getEndTime(new Date());
            List<String> refererList = accessLogService.listByReferer(fromDate, toDate);
            List<SimpleObject> objectList = new ArrayList<>();
            refererList.forEach(referer -> {
                int count = accessLogService.countByReferer(referer, null, DateUtil.getStartTime(fromDate), toDate);
                SimpleObject object = new SimpleObject(referer, String.valueOf(count));
                objectList.add(object);
            });
            Collections.sort(objectList, new Comparator<SimpleObject>() {
                @Override
                public int compare(SimpleObject o1, SimpleObject o2) {
                    return Integer.valueOf(o2.getValue()).compareTo(Integer.valueOf(o1.getValue()));
                }
            });
            PageInfo pageInfo = new PageInfo(refererList, 5);
            pageInfo.setList(objectList);
            return Msg.success().add("pageInfo", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @GetMapping(value = "/useragent")
    public Msg useragent(@RequestParam(value = "pn", defaultValue = "1", required = false) Integer pn,
                         @RequestParam(value = "size", defaultValue = "1000", required = false) Integer size) {
        try {
            PageHelper.startPage(pn, size);
            Date fromDate = DateUtil.getStartTime(new Date());
            Date toDate = DateUtil.getEndTime(new Date());
            List<String> useragentList = accessLogService.listByUserAgent(fromDate, toDate);
            List<SimpleObject> objectList = new ArrayList<>();
            useragentList.forEach(useragent -> {
                int count = accessLogService.countByUserAgent(useragent, null, DateUtil.getStartTime(fromDate), toDate);
                SimpleObject object = new SimpleObject(useragent, String.valueOf(count));
                objectList.add(object);
            });
            Collections.sort(objectList, new Comparator<SimpleObject>() {
                @Override
                public int compare(SimpleObject o1, SimpleObject o2) {
                    return Integer.valueOf(o2.getValue()).compareTo(Integer.valueOf(o1.getValue()));
                }
            });
            PageInfo pageInfo = new PageInfo(useragentList, 5);
            pageInfo.setList(objectList);
            return Msg.success().add("pageInfo", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }
}
