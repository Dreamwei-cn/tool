package com.ufc.channel.common.mapper;

import com.ufc.channel.common.domain.ChannelAppInterface;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【channel_app_interface】的数据库操作Mapper
* @createDate 2023-04-10 15:59:00
* @Entity com.ufc.channel.common.domain.ChannelAppInterface
*/
@Mapper
public interface ChannelAppInterfaceMapper extends BaseMapper<ChannelAppInterface> {

    List<ChannelAppInterface> getAll(@Param("appId")Long appId);

    void addChannelAppInterface(ChannelAppInterface channelAppInterface);

    int updateChannelAppInterface(ChannelAppInterface channelAppInterface);

    int delChannelAppInterface(@Param("id")Long id);

    ChannelAppInterface selectByAppAndPath(@Param("appId") Long appId, @Param("apiUrl") String path);
}




