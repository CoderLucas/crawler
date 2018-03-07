package com.lujh.controller;

import com.lujh.bean.AccessLogListOut;
import com.lujh.service.AccessLogService;
import com.lujh.util.DateUtil;
import com.lujh.util.Msg;
import com.lujh.util.enums.AccessLogStatus;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lujianhao on 2018/3/4.
 */
@RestController
@RequestMapping(value = "/accesslog")
public class AccessLogController {
    @Autowired
    private AccessLogService accessLogService;

    /**
     * 获取当日的所有IP访问记录
     *
     * @return
     */
    @GetMapping(value = "/ip/list")
    public Msg ipList() {
        try {
            List<String> ipList = accessLogService.listByIP(DateUtil.getStartTime(new Date()), new Date());
            List<AccessLogListOut> accessLogListOutList = new LinkedList<>();
            ipList.forEach(ip -> {
                AccessLogListOut accessLogListOut = new AccessLogListOut();
                accessLogListOut.setIp(ip);
                accessLogListOut.setSuccessNumber(accessLogService.countByIP(ip, AccessLogStatus.SUCCESS, DateUtil.getStartTime(new Date()), new Date()));
                accessLogListOut.setFailNumber(accessLogService.countByIP(ip, AccessLogStatus.FAIL, DateUtil.getStartTime(new Date()), new Date()));
                accessLogListOut.setFromTime(DateFormatUtils.format(DateUtil.getStartTime(new Date()), DateUtil.DATE_FORMAT_PATTERN_DEFAULT));
                accessLogListOut.setToTime(DateFormatUtils.format(new Date(), DateUtil.DATE_FORMAT_PATTERN_DEFAULT));
                accessLogListOutList.add(accessLogListOut);
            });
            int total = accessLogService.count(null, DateUtil.getStartTime(new Date()), new Date());
            return Msg.success().add("ip_list", accessLogListOutList).add("total", total);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    /**
     * 按小时分段
     *
     * @param ip
     * @param hour
     * @return
     */
    @GetMapping(value = "/ip")
    public Msg ip(@RequestParam(value = "ip") String ip,
                  @RequestParam(value = "hour") int hour) {
        try {
            List<Date> dateList = DateUtil.dateList(hour);
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
            return Msg.success().add("list", accessLogListOutList);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }
}
