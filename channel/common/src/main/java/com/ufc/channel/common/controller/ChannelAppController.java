package com.ufc.channel.common.controller;

import com.ufc.channel.common.bean.ResponseResult;
import com.ufc.channel.common.domain.ChannelApp;
import com.ufc.channel.common.service.ChannelAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channelApp")
public class ChannelAppController {

    @Autowired
    private ChannelAppService channelAppService;

    @RequestMapping("/queryAll")
    public ResponseResult getAll(@RequestBody ChannelApp channelApp){
        //后期看前端是否是以post方式进行查询
        String pageSize = channelApp.getPageSize();
        String pageNum = channelApp.getPageNum();
        return channelAppService.getAll(pageSize,pageNum);
    }

    @RequestMapping("/query")
    public ResponseResult getByName(@RequestBody ChannelApp channelApp){
        String pageSize = channelApp.getPageSize();
        String pageNum = channelApp.getPageNum();
        String name = channelApp.getAppName();
        return channelAppService.getByName(name,pageSize,pageNum);
    }

    @RequestMapping("/add")
    public ResponseResult addChannelApp(@RequestBody ChannelApp channelApp){
        return channelAppService.addChannelApp(channelApp);
    }

    @RequestMapping("/upde")
    public ResponseResult updateChannelApp(@RequestBody ChannelApp channelApp){
        return channelAppService.updateChannelApp(channelApp);
    }

}
