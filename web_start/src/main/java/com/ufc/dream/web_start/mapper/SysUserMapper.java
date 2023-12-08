package com.ufc.dream.web_start.mapper;

import com.ufc.dream.web_start.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2023-12-06 15:08:36
* @Entity com.ufc.dream.web_start.entity.SysUser
*/
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     *  根据 名称查询用户
     * @param name
     * @return
     */
    SysUser selectOneByName(String name);

    List<SysUser> selectUserList(int count);

    int insertEntity(SysUser user);

    int insertEntitySql(List<SysUser> list);

    int updateUserNameById(SysUser user);

    int updateUserNameByIdCase(List<SysUser> list);

    int updateUserNameByIdSql(List<SysUser> list);

}




