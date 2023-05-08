package com.ufc.channel.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ufc.channel.common.annotations.ChannelAfter;
import com.ufc.channel.common.bean.ResponseResult;
import com.ufc.channel.common.domain.ChannelAppBackLog;
import com.ufc.channel.common.service.ChannelAppBackLogService;
import com.ufc.channel.common.mapper.ChannelAppBackLogMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author Administrator
* @description 针对表【channel_app_back_log】的数据库操作Service实现
* @createDate 2023-04-10 15:58:50
*/
@Service
public class ChannelAppBackLogServiceImpl extends ServiceImpl<ChannelAppBackLogMapper, ChannelAppBackLog>
    implements ChannelAppBackLogService{

    @Override
    @ChannelAfter(value = "/path", name ="name")
    public ResponseResult testBack(JSONObject jsonObject) {
        jsonObject.put("appKey","test");
        jsonObject.put("orderNo","orderNo");
        return new ResponseResult(ResponseResult.CodeStatus.OK, "", jsonObject);
    }

    @Override
    public void addBackLog(Long appId, Long interfaceId, String data, String content, String orderNo,String response) {
        ChannelAppBackLog backLog = new ChannelAppBackLog();
        backLog.setAppid(appId);
        backLog.setInterfaceid(interfaceId);
        backLog.setData(data);
        backLog.setContent(content);
        backLog.setOrderno(orderNo);
        backLog.setResponse(response);
        backLog.setCreatedate(new Date());
        getBaseMapper().insert(backLog);
    }
}




