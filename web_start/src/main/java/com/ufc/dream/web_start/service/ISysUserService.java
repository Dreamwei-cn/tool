package com.ufc.dream.web_start.service;

import com.ufc.dream.web_start.entity.SysUser;

import java.util.List;


/**
* @author Administrator
* @description 针对表【sys_user】的数据库操作Service
* @Date 2023-12-06 14:50:47
*/
public interface ISysUserService extends IBaseService<SysUser> {

    /**
     *  单用户查询
     * @param name userName
     * @return @link com.ufc.dream.web_start.entity.SysUser
     */
    SysUser findOneUser(String name);


    /**
     * 使用mybatisplus 批量插入
     * @param list 用户集合
     * @return @link com.ufc.dream.web_start.entity.SysUser
     */
    int saveBatchMybatisPlus(List<SysUser> list);

    /**
     *  使用 循环插入
     * @param list
     * @return
     */
    int saveForeach(List<SysUser> list);

    /**
     * 使用sqlSession批量插入
     * @param list
     * @return
     */
    int saveSession(List<SysUser> list);

    /**
     *  拼装sql批量插入
     * @param list
     * @return
     */
    int saveSql(List<SysUser> list);


    /**
     *  根据 id 使用mybatisplus 批量插入
     * @param list
     * @return
     */
    int updateBatchWithId(List<SysUser> list);


    /**
     *  使用 case when 批量更新
     * @param list
     * @return
     */
    int updateCase(List<SysUser> list);


    /**
     *  使用 拼装sql批量更新
     * @param list
     * @return
     */
    int updateSql(List<SysUser> list);

    /**
     * 使用 sqlsession 批量更新
     * @param list
     * @return
     */
    int updateSession(List<SysUser> list);

    /**
     *  使用foreach 批量更新
     * @param list
     * @return
     */
    int updateForeach(List<SysUser> list);


    /**
     *  查询测试更新的数据
     * @return
     */
    List<SysUser> queryTestList(int start, int num);

}
