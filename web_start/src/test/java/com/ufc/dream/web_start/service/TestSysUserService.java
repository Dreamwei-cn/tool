package com.ufc.dream.web_start.service;

import com.ufc.dream.web_start.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TestSysUserService {

    @Resource
    private ISysUserService sysUserServiceImpl;



    @Test
    public void test(){
        SysUser user = new SysUser();
        user.setUserNo("1");
        user.setUserName("username");
        sysUserServiceImpl.save(user);
        SysUser username = sysUserServiceImpl.findOneUser("username");
        System.out.println(username);
    }

    @Test
    public void testSave(){

    }
}
