package com.ufc.dream.web_start.config.redisession;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author mengw
 */
@Slf4j
@Configuration
public class RedissonConfig {

    @Autowired
    private RedissonClient redissonClient;

    @Bean
    public RedissonLocker redissonLocker() {
        RedissonLocker redissonLocker = new RedissonLocker(redissonClient);
        LockerUtil.setLocker(redissonLocker);
        return redissonLocker;
    }
}