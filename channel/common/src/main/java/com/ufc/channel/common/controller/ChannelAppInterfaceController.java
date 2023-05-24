package com.ufc.channel.common.controller;

import com.ufc.channel.common.bean.ResponseResult;
import com.ufc.channel.common.domain.ChannelAppInterface;
import com.ufc.channel.common.service.ChannelAppInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appInt")
public class ChannelAppInterfaceController {

    @Autowired
    private ChannelAppInterfaceService channelAppInterfaceService;

    @RequestMapping("/queryAll")
    public ResponseResult getAll(@RequestBody ChannelAppInterface channelAppInterface){
        //后期看前端是否是以post方式进行查询
        return channelAppInterfaceService.getAll(channelAppInterface);
    }

    @RequestMapping("/add")
    public ResponseResult addChannelAppIn(@RequestBody ChannelAppInterface channelAppInterface){
        return channelAppInterfaceService.addChannelAppIn(channelAppInterface);
    }

    @RequestMapping("/upde")
    public ResponseResult updateChannelAppIn(@RequestBody ChannelAppInterface channelAppInterface){
        return channelAppInterfaceService.updChannelAppIn(channelAppInterface);
    }

    @RequestMapping("/del")
    public ResponseResult delChannelAppIn(@RequestBody ChannelAppInterface channelAppInterface){
        return channelAppInterfaceService.delChannelIn(channelAppInterface);
    }

}
