package com.ufc.dream.web_start.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ufc.dream.web_start.entity.SysUser;
import com.ufc.dream.web_start.mapper.SysUserMapper;
import com.ufc.dream.web_start.service.ISysUserService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Queue;

/**
* @author Administrator
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-12-06 15:08:36
*/
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser>
    implements ISysUserService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public SysUser findOneUser(String name) {
        return getBaseMapper().selectOneByName(name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveBatchMybatisPlus(List<SysUser> list) {
        saveBatch(list);
        return list.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveForeach(List<SysUser> list) {
        for (SysUser user : list) {
            getBaseMapper().insertEntity(user);
        }
        return list.size();
    }

    @Override
    public int saveSession(List<SysUser> list) {

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            // 获取批量操作的新的map
            SysUserMapper mapper = batchSqlSession.getMapper(SysUserMapper.class);
            for (SysUser user : list) {
                mapper.insertEntity(user);
            }
            // session 提交
            batchSqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
            batchSqlSession.rollback();
        } finally {
            // session 关闭
            batchSqlSession.close();
        }
        return list.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveSql(List<SysUser> list) {
        int size = list.size();
        int num = size/1000;
        if (size%1000!=0){
            num += 1;
        }
        for (int i = 0; i < num; i++) {
            if (i == num - 1) {
                getBaseMapper().insertEntitySql(list.subList(i*1000, list.size()));
            }
            getBaseMapper().insertEntitySql(list.subList(i*1000, (i+1) * 1000));
        }

        return list.size();
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatchWithId(List<SysUser> list) {
        updateBatchById(list);
        return list.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCase(List<SysUser> list) {
        getBaseMapper().updateUserNameByIdSql(list);
        return list.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSql(List<SysUser> list) {
        getBaseMapper().updateUserNameByIdSql(list);
        return list.size();
    }

    @Override
    public int updateSession(List<SysUser> list) {
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            // 获取批量操作的新的map
            SysUserMapper mapper = batchSqlSession.getMapper(SysUserMapper.class);

            for (SysUser user : list) {
                mapper.updateUserNameById(user);
            }
            // session 提交
            batchSqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
            batchSqlSession.rollback();
        } finally {
            // session 关闭
            batchSqlSession.close();
        }
        return list.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateForeach(List<SysUser> list) {
        for (SysUser user : list) {
            getBaseMapper().updateUserNameById(user);
        }
        return list.size();
    }

    @Override
    public List<SysUser> queryTestList(int start, int num) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.last("limit " + num + "  offset " + num);
        return list(queryWrapper);
    }
}




