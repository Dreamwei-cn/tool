package com.ufc.channel.common.service;

import com.ufc.channel.common.bean.ResponseResult;
import com.ufc.channel.common.domain.ChannelApp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【channel_app】的数据库操作Service
* @createDate 2023-04-10 15:58:09
*/
public interface ChannelAppService {

    ResponseResult getAll(String pageSize,String pageNum);

    ResponseResult getByName(String name,String pageSize,String pageNum);

    ResponseResult addChannelApp(ChannelApp channelApp);

    ResponseResult updateChannelApp(ChannelApp channelApp);

    ResponseResult delChannelApp(String id);

    String queryPriKeyById(Long appId);

    ChannelApp queryByAppKey(String appKey);
}
