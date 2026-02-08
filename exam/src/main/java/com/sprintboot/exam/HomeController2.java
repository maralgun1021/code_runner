package com.sprintboot.exam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController2 {

    @Value("${spring.application.name}")
    private String test;

    @RequestMapping("/")
    public String index(){
        String view = getViewName();
        System.out.println("appName: " + test);
        return view;
    }

    private String getViewName(){
        return "index.html";
    }
}