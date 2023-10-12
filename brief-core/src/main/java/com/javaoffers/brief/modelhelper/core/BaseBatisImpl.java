package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.config.BriefProperties;
import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.brief.modelhelper.exception.BriefException;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.jdbc.BriefJdbcExecutor;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutor;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: core implementation class
 * @Auther: create by cmj on 2022/05/22 02:56
 */
public class BaseBatisImpl<T, ID> implements BaseBatis<T> {

    private static JdbcExecutorFactory jdbcExecutorFactory;
    static {
        jdbcExecutorFactory = BriefProperties.getJdbcExecutorFactory();
    }

    private JdbcExecutor<T> jdbcExecutor;

    public static <T, ID> BaseBatis getInstance(HeadCondition headCondition) {
        return getInstance(headCondition.getDataSource(), headCondition.getModelClass());
    }

    private static <T, ID> BaseBatis getInstance(DataSource dataSource, Class mClass) {
        BaseBatisImpl batis = new BaseBatisImpl(dataSource,mClass);
        return new BaseBatisImplProxy(batis, mClass);
    }

    private BaseBatisImpl(DataSource dataSource,Class modelClass) {
        this.jdbcExecutor = jdbcExecutorFactory.createJdbcExecutor(dataSource, modelClass);
    }

    /****************************crud****************************/
    public int saveData(String sql) {
        return saveData(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int saveData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(sql, map);
        return this.jdbcExecutor.save(sql_).toInt();
    }

    public int deleteData(String sql) {
        return deleteData(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int deleteData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(sql, map);
        return this.jdbcExecutor.modify(sql_);
    }

    public int updateData(String sql) {
        return updateData(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int updateData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(sql, map);
        return this.jdbcExecutor.modify(sql_);
    }

    /*********************************Support Model*********************************/
    public List<T> queryData(String sql) {
        return this.queryData(sql, new HashMap<>());
    }

    @Override
    public List<T> queryData(String sql, Map<String, Object> paramMap) {
        List<Map<String, Object>> paramMapList = new ArrayList<>();
        paramMapList.add(paramMap);
        SQL querySql = SQLParse.parseSqlParams(sql, paramMapList);
        return this.jdbcExecutor.queryList(querySql);
    }

    /*********************************batch processing*********************************/
    public Integer batchUpdate(String sql, List<Map<String, Object>> paramMap) {
        SQL batchSQL = SQLParse.parseSqlParams(sql, paramMap);
        return this.jdbcExecutor.batchModify(batchSQL);
    }

    @Override
    public List<Id> batchInsert(String sql, List<Map<String, Object>> paramMap) {
        SQL pss = SQLParse.parseSqlParams(sql, paramMap);
        return this.jdbcExecutor.batchSave(pss);
    }

}
