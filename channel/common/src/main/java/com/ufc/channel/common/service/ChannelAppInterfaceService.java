package com.ufc.channel.common.service;

import com.ufc.channel.common.bean.ResponseResult;
import com.ufc.channel.common.domain.ChannelAppInterface;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【channel_app_interface】的数据库操作Service
* @createDate 2023-04-10 15:59:00
*/
public interface ChannelAppInterfaceService extends IService<ChannelAppInterface> {

    ResponseResult getAll(ChannelAppInterface channelAppInterface);

    ResponseResult addChannelAppIn(ChannelAppInterface channelAppInterface);

    ResponseResult updChannelAppIn(ChannelAppInterface channelAppInterface);

    ResponseResult delChannelIn(ChannelAppInterface channelAppInterface);

}
