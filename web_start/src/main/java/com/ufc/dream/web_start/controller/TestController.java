package com.ufc.dream.web_start.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/name")
    public String defaultTest(String name){
        return " this is test, " + name;
    }
}
