package com.ufc.channel.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ufc.channel.common.bean.ResponseResult;
import com.ufc.channel.common.domain.ChannelApp;
import com.ufc.channel.common.service.ChannelAppService;
import com.ufc.channel.common.mapper.ChannelAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【channel_app】的数据库操作Service实现
* @createDate 2023-04-10 15:58:09
*/
@Service
public class ChannelAppServiceImpl implements ChannelAppService{

    @Autowired
    private ChannelAppMapper channelAppMapper;


    //查询全部信息
    @Override
    public ResponseResult getAll(String pageSize, String pageNum) {
        //第几页
        Integer num = Integer.valueOf(pageNum);
        //每页数量
        Integer size = Integer.valueOf(pageSize);
        String orderBy = "id";
        PageHelper.startPage(num,size,orderBy);
        List<ChannelApp> getChannel = channelAppMapper.selectAll();
        System.out.println(getChannel);
        PageInfo<ChannelApp> pageInfo = new PageInfo<>(getChannel);
        return new ResponseResult(ResponseResult.CodeStatus.OK,pageInfo);
    }

    //模糊查询单个信息
    @Override
    public ResponseResult getByName(String name, String pageSize, String pageNum) {
        //每页条数
        Integer size = Integer.valueOf(pageSize);
        //第几页
        Integer num = Integer.valueOf(pageNum);
        String orderBy = "id";
        PageHelper.startPage(num,size,orderBy);
        List<ChannelApp> getByName = channelAppMapper.selectByName(name);
        PageInfo<ChannelApp> pageInfo = new PageInfo<>(getByName);
        return new ResponseResult(ResponseResult.CodeStatus.OK,pageInfo);
    }

    //新增channel_app信息
    @Override
    public ResponseResult addChannelApp(ChannelApp channelApp) {

        Date date = new Date();
//        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = format0.format(date);
        channelApp.setCreateDate(date);
        channelAppMapper.insertChannelApp(channelApp);

        return new ResponseResult(ResponseResult.CodeStatus.OK);
    }

    //修改channel_app信息
    @Override
    public ResponseResult updateChannelApp(ChannelApp channelApp) {
        int num = channelAppMapper.updateChannelApp(channelApp);
        if(num>=1){
            return new ResponseResult(ResponseResult.CodeStatus.OK);
        }else{
            return new ResponseResult(ResponseResult.CodeStatus.FAIL);
        }
    }

    //删除单条数据
    @Override
    public ResponseResult delChannelApp(String id) {
        int num = channelAppMapper.deleteChannelApp(id);
        if(num>=1){
            return new ResponseResult(ResponseResult.CodeStatus.OK);

        }else{
            return new ResponseResult(ResponseResult.CodeStatus.FAIL);
        }
    }

    @Override
    public String queryPriKeyById(Long appId) {
        return channelAppMapper.selectById(appId).getPriKey();
    }

    @Override
    public ChannelApp queryByAppKey(String appKey) {
        return channelAppMapper.selectByAppKey(appKey);
    }


}




