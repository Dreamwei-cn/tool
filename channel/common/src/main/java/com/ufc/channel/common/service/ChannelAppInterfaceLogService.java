package com.ufc.channel.common.service;

import com.ufc.channel.common.domain.ChannelAppInterfaceLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【channel_app_interface_log】的数据库操作Service
* @createDate 2023-04-10 15:59:05
*/
public interface ChannelAppInterfaceLogService extends IService<ChannelAppInterfaceLog> {

    void addLog(String path, Long appId, String result, String content);
}
