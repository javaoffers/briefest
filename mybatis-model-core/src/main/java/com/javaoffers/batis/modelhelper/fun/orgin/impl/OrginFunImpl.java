package com.javaoffers.batis.modelhelper.fun.orgin.impl;

import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
import com.javaoffers.batis.modelhelper.fun.orgin.OrginFun;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mingJie
 */
public class OrginFunImpl implements OrginFun {

    private BaseBatisImpl baseBatis;

    public OrginFunImpl(JdbcTemplate jdbcTemplate){
        baseBatis =BaseBatisImpl.getInstance(jdbcTemplate);
    }

    @Override
    public Map<String, Object> selectOne(String sql, Map<String, Object> param) {
        List<Map<String, Object>>  list = baseBatis.queryData(sql, param);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        // not use  Collections.EMPTY_MAP
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> selectOne(String sql) {
        return selectOne(sql, Collections.EMPTY_MAP);
    }
}
