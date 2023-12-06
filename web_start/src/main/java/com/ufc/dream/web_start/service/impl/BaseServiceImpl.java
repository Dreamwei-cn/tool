package com.ufc.dream.web_start.service.impl;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ufc.dream.web_start.service.IBaseService;

import java.util.Collection;

/**
 * @author 思维穿梭
 * mybatisplus 服务实现类
 */
public class BaseServiceImpl <M extends BaseMapper<T>, T > extends ServiceImpl<M, T> implements IBaseService<T> {


    @Override
    public boolean saveBatch(Collection<T> entityList) {
        String sqlStatement = this.sqlStatement(SqlMethod.INSERT_ONE);
        return this.executeBatch(entityList, 1000, (sqlSession, entity) -> {
            sqlSession.insert(sqlStatement, entity);
        });
    }
}
