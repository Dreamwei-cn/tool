package com.ufc.channel.common.service;

import com.alibaba.fastjson.JSONObject;
import com.ufc.channel.common.bean.ResponseResult;
import com.ufc.channel.common.domain.ChannelAppBackLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【channel_app_back_log】的数据库操作Service
* @createDate 2023-04-10 15:58:50
*/
public interface ChannelAppBackLogService extends IService<ChannelAppBackLog> {

    ResponseResult testBack(JSONObject jsonObject);

    void addBackLog(Long appId, Long interfaceId, String data, String content, String orderNo, String response);
}
