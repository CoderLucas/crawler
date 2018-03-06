package com.lujh.controller;

import com.lujh.bean.AccessLogListOut;
import com.lujh.service.AccessLogService;
import com.lujh.util.DateUtil;
import com.lujh.util.Msg;
import com.lujh.util.enums.AccessLogStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
                accessLogListOutList.add(accessLogListOut);
            });
            return Msg.success().add("ip_list", accessLogListOutList);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    @GetMapping(value = "/ip")
    public Msg ip(@RequestParam(value = "ip") String ip,
                  @RequestParam(value = "hour") int hour) {
        try {
            List<Date> dateList = new ArrayList<>();


            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }
}
