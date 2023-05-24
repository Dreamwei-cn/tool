package com.ufc.channel.common.mapper;

import com.ufc.channel.common.domain.ChannelApp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【channel_app】的数据库操作Mapper
* @createDate 2023-04-10 15:58:09
* @Entity com.ufc.channel.common.domain.ChannelApp
*/
@Mapper
public interface ChannelAppMapper {

    List<ChannelApp> selectAll();

    List<ChannelApp> selectByName(@Param("name") String name);

    void insertChannelApp(ChannelApp channelApp);

    int updateChannelApp(ChannelApp channelApp);

    int deleteChannelApp(@Param("id") String id);

    ChannelApp selectById(Long appId);

    ChannelApp selectByAppKey(String appKey);
}




