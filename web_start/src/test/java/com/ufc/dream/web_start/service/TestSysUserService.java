package com.ufc.dream.web_start.service;

import com.ufc.dream.web_start.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    /**
     *  插入10000条耗时
     *  Foreach: 2452
     * Session: 277
     * Sql: 602
     * mybatisPlus: 436
     */
    @Test
    public void testSave(){
        List<SysUser> list = new ArrayList<SysUser>();
        SysUser user = null;
        for (int i = 0; i < 10000; i++) {
            user = new SysUser();
            user.setUserNo("1");
            user.setUserName("username");
            list.add(user);
        }
        long start = System.currentTimeMillis();
        sysUserServiceImpl.saveForeach(list);
        long foreachEnd = System.currentTimeMillis();
        sysUserServiceImpl.saveSession(list);
        long sessionEnd = System.currentTimeMillis();
        sysUserServiceImpl.saveSql(list);
        long sqlEnd = System.currentTimeMillis();
        sysUserServiceImpl.saveBatchMybatisPlus(list);
        long mybatisPlusEnd = System.currentTimeMillis();

        System.out.println("Foreach: " + (foreachEnd - start));
        System.out.println("Session: " + (sessionEnd - foreachEnd));
        System.out.println("Sql: " + (sqlEnd - sessionEnd));
        System.out.println("mybatisPlus: " + (mybatisPlusEnd - sqlEnd));
    }

    /**
     *  更新10000 条耗时
     *  Foreach: 1570
     * Session: 344
     * Sql: 1283
     * Case: 1076
     * mybatisPlus: 510
     */
    @Test
    public void testUpdate(){
        List<SysUser> listFor = sysUserServiceImpl.queryTestList(1,10000);
        List<SysUser> listSes = sysUserServiceImpl.queryTestList(10000,10000);
        List<SysUser> listSql = sysUserServiceImpl.queryTestList(20000,10000);
        List<SysUser> listCase= sysUserServiceImpl.queryTestList(30000,10000);
        List<SysUser> listPlus = sysUserServiceImpl.queryTestList(40000,10000);

        long start = System.currentTimeMillis();
        sysUserServiceImpl.updateForeach(listFor);
        long foreachEnd = System.currentTimeMillis();
        sysUserServiceImpl.updateSession(listSes);
        long sessionEnd = System.currentTimeMillis();
        sysUserServiceImpl.updateSql(listSql);
        long sqlEnd = System.currentTimeMillis();
        sysUserServiceImpl.updateCase(listCase);
        long caseEnd = System.currentTimeMillis();
        sysUserServiceImpl.updateBatchWithId(listPlus);
        long plusEnd = System.currentTimeMillis();

        System.out.println("Foreach: " + (foreachEnd - start));
        System.out.println("Session: " + (sessionEnd - foreachEnd));
        System.out.println("Sql: " + (sqlEnd - sessionEnd));
        System.out.println("Case: " + (caseEnd - sqlEnd));
        System.out.println("mybatisPlus: " + (plusEnd - caseEnd));
    }
}
