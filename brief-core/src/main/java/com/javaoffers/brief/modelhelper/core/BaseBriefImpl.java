package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.context.SmartBriefContext;
import com.javaoffers.brief.modelhelper.context.SmartBriefProperties;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
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
public class BaseBriefImpl<T, ID> implements BaseBrief<T>, BriefContextPostProcess {

    private static JdbcExecutorFactory jdbcExecutorFactory;

    private JdbcExecutor<T> jdbcExecutor;

    public BaseBriefImpl() {
    }

    public static <T, ID> BaseBrief getInstance(HeadCondition headCondition) {
        return getInstance(headCondition.getDataSource(), headCondition.getModelClass());
    }

    private static <T, ID> BaseBrief getInstance(DataSource dataSource, Class mClass) {
        BaseBriefImpl batis = new BaseBriefImpl(dataSource,mClass);
        return new BaseBriefImplProxy(batis, mClass);
    }

    private BaseBriefImpl(DataSource dataSource, Class modelClass) {
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

    @Override
    public void postProcess(BriefContext briefContext) {
        SmartBriefContext smartBriefContext = (SmartBriefContext) briefContext;
        jdbcExecutorFactory = smartBriefContext.getBriefProperties(SmartBriefProperties.class).get(0).getJdbcExecutorFactory();
    }
}