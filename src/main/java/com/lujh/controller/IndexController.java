package com.lujh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by CoderLucas on 2018/3/18.
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/404")
    public String error() {
        return "/404";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

}
