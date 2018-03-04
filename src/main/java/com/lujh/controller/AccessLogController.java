package com.lujh.controller;

import com.lujh.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
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


}
