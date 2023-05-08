package com.ufc.channel.common.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {"com.ufc.channel.common"}
)
@MapperScan("com.ufc.channel.common.mapper")
public class ChannelConfig {

}

