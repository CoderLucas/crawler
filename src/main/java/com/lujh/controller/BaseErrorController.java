package com.lujh.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lujianhao on 2018/3/24.
 */
@Controller
@RequestMapping(value = "error")
public class BaseErrorController implements ErrorController{

    @Override
    public String getErrorPath() {
        return "/404";
    }

    @RequestMapping
    public String error() {
        return getErrorPath();
    }

}
