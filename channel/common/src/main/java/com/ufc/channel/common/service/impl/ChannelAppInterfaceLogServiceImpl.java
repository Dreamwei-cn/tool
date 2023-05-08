package com.ufc.channel.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ufc.channel.common.domain.ChannelAppInterface;
import com.ufc.channel.common.domain.ChannelAppInterfaceLog;
import com.ufc.channel.common.mapper.ChannelAppInterfaceMapper;
import com.ufc.channel.common.service.ChannelAppInterfaceLogService;
import com.ufc.channel.common.mapper.ChannelAppInterfaceLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author Administrator
* @description 针对表【channel_app_interface_log】的数据库操作Service实现
* @createDate 2023-04-10 15:59:05
*/
@Service
public class ChannelAppInterfaceLogServiceImpl extends ServiceImpl<ChannelAppInterfaceLogMapper, ChannelAppInterfaceLog>
    implements ChannelAppInterfaceLogService{


    @Autowired
    private ChannelAppInterfaceMapper channelAppInterfaceMapper;

    @Override
    public void addLog(String path, Long appId, String result, String content) {
        ChannelAppInterfaceLog interfaceLog = new ChannelAppInterfaceLog();
        ChannelAppInterface appInterface = channelAppInterfaceMapper.selectByAppAndPath(appId, path);
        interfaceLog.setAppid(appId);
        interfaceLog.setCreatedate(new Date());
        interfaceLog.setInterfaceid(appInterface.getId());
        interfaceLog.setData(result);
        interfaceLog.setContent(content);
        getBaseMapper().insert(interfaceLog);
    }
}




