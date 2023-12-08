package com.ufc.dream.web_start.service;

import com.ufc.dream.web_start.config.redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRedisService {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test(){
        redisUtil.set("test", "test");
        String test = redisUtil.getString("test");

        System.out.println(test);
    }
}
