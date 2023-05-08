package com.ufc.channel.common.controller;

import com.alibaba.fastjson.JSONObject;

import com.ufc.channel.common.annotations.ChannelBefore;
import com.ufc.channel.common.bean.ResponseResult;
import com.ufc.channel.common.service.ChannelAppBackLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private ChannelAppBackLogService channelAppBackLogServiceImpl;

//    @ChannelAfter(value = "/path", name ="name")
    @ChannelBefore(value = "/path",name = "name")
    @PostMapping("/path")
    public ResponseResult testPath( @RequestBody JSONObject jsonObject){
        System.out.println("controller:   this is controller");
        System.out.println(jsonObject.toString());
        channelAppBackLogServiceImpl.testBack(jsonObject);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "path");
    }


    @PostMapping("/after")
    public ResponseResult testAfter(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject.toString());
        return new ResponseResult(ResponseResult.CodeStatus.OK, "path");
    }
}
