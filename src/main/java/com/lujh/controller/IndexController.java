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

    @GetMapping("/catalog")
    public String catalog() {
        return "/catalog";
    }

    @GetMapping("/product_page")
    public String product_page(){
        return "/product_page";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/ip")
    public String ip(){
        return "ip";
    }

    @GetMapping("/useragent")
    public String useragent(){
        return "useragent";
    }

    @GetMapping("/referer")
    public String referer(){
        return "referer";
    }
}
