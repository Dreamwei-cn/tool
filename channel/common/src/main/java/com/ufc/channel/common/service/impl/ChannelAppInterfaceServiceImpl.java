package com.ufc.channel.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ufc.channel.common.bean.ResponseResult;
import com.ufc.channel.common.domain.ChannelAppInterface;
import com.ufc.channel.common.service.ChannelAppInterfaceService;
import com.ufc.channel.common.mapper.ChannelAppInterfaceMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【channel_app_interface】的数据库操作Service实现
* @createDate 2023-04-10 15:59:00
*/
@Service
public class ChannelAppInterfaceServiceImpl extends ServiceImpl<ChannelAppInterfaceMapper, ChannelAppInterface>
    implements ChannelAppInterfaceService{

    @Autowired
    private ChannelAppInterfaceMapper channelAppInterfaceMapper;


    @Override
    public ResponseResult getAll(ChannelAppInterface channelAppInterface) {
        Long appId = channelAppInterface.getAppid();
        List<ChannelAppInterface> getAll = channelAppInterfaceMapper.getAll(appId);
        return new ResponseResult(ResponseResult.CodeStatus.OK,getAll);
    }

    @Override
    public ResponseResult addChannelAppIn(ChannelAppInterface channelAppInterface) {
        channelAppInterfaceMapper.addChannelAppInterface(channelAppInterface);
        return new ResponseResult(ResponseResult.CodeStatus.OK);
    }

    @Override
    public ResponseResult updChannelAppIn(ChannelAppInterface channelAppInterface) {
        int num = channelAppInterfaceMapper.updateChannelAppInterface(channelAppInterface);

        if(num>=1){
            return new ResponseResult(ResponseResult.CodeStatus.OK);
        }else{
            return new ResponseResult(ResponseResult.CodeStatus.FAIL);
        }

    }

    @Override
    public ResponseResult delChannelIn(ChannelAppInterface channelAppInterface) {
        Long id = channelAppInterface.getId();
        int num = channelAppInterfaceMapper.delChannelAppInterface(id);
        if(num>=1){
            return new ResponseResult(ResponseResult.CodeStatus.OK);
        }else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL);
        }
    }

}




