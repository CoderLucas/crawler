package com.lujh.controller;

import com.lujh.service.AccessLogService;
import com.lujh.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lujianhao on 2018/3/4.
 */
@RestController
@RequestMapping(value = "/accesslog")
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    @GetMapping(value = "/ip/list")
    public Msg ipList(){
        try {
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail();
        }
    }
}
