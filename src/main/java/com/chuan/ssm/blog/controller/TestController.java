package com.chuan.ssm.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/quick")
    public String test(){
        System.out.println("Controller test running...");
        return "testSpringMVC";
    }
}
