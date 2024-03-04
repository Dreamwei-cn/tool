package com.ufc.dream.web_start.service;

import com.ufc.dream.web_start.config.redis.RedisUtil;
import com.ufc.dream.web_start.config.redisession.LockerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TestRedisService {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test(){
        redisUtil.set("test", "test");
        String test = redisUtil.getString("test");
        try {
            boolean hasLock = LockerUtil.tryLock("RedisLocal", 15, 15, TimeUnit.SECONDS);
            if (hasLock) {
                System.out.println(" 加锁成功");
            }
        }finally {
            LockerUtil.unlock("RedisLocal");
            System.out.println("解锁完成");
        }

    }
}
