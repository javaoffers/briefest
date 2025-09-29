package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.context.*;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutor;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;
import com.javaoffers.brief.modelhelper.parser.StatementParser;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.SQLType;
import com.javaoffers.brief.modelhelper.utils.TableHelper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Description: core implementation class
 * @Auther: create by cmj on 2022/05/22 02:56
 */
public class BaseBriefImpl<T, ID> implements BaseBrief<T>, BriefContextAware {

    private static volatile SmartBriefContext smartBriefContext;

    private JdbcExecutor<T> jdbcExecutor;

    private DBType dbType;

    public BaseBriefImpl() {}

    protected BaseBriefImpl(DataSource dataSource, Class modelClass) {
        this.jdbcExecutor = smartBriefContext.getJdbcExecutorFactory().createJdbcExecutor(dataSource, modelClass);
        this.dbType = TableHelper.getTableInfo(modelClass).getDbType();
    }

    @Override
    public int saveData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(this.dbType, sql, map);
        return this.jdbcExecutor.save(sql_).toInt();
    }

    @Override
    public int deleteData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(this.dbType, sql, map);
        return this.jdbcExecutor.modify(sql_);
    }

    @Override
    public int updateData(String sql, Map<String, Object> map) {
        SQL sql_ = SQLParse.getSQL(this.dbType, sql, map);
        return this.jdbcExecutor.modify(sql_);
    }

    @Override
    public List<T> queryData(String sql, Map<String, Object> paramMap) {
        List<Map<String, Object>> paramMapList = new ArrayList<>();
        paramMapList.add(paramMap);
        SQL querySql = SQLParse.parseSqlParams(this.dbType, sql, paramMapList);
        return this.jdbcExecutor.queryList(querySql);
    }

    @Override
    public int queryStream(String sql, Map<String, Object> paramMap, Consumer<T> consumer) {
        List<Map<String, Object>> paramMapList = new ArrayList<>();
        paramMapList.add(paramMap);
        SQL querySql = SQLParse.parseSqlParams(this.dbType, sql, paramMapList);
        querySql.setStreaming(consumer);
        return this.jdbcExecutor.queryStream(querySql);
    }

    @Override
    public List<Object> nativeData(String sql, Map<String, Object> paramMap, SQLType sqlType) {
        List<Map<String, Object>> paramMapList = new ArrayList<>();
        paramMapList.add(paramMap);
        SQL querySql = SQLParse.parseSqlParams(this.dbType, sql, paramMapList);
        querySql.setSqlType(sqlType);
        return (List) this.jdbcExecutor.queryList(querySql);
    }

    @Override
    public void nativeData(String sql, Map<String, Object> paramMap, SQLType sqlType, Consumer<T> consumer) {
        List<Map<String, Object>> paramMapList = new ArrayList<>();
        paramMapList.add(paramMap);
        SQL querySql = SQLParse.parseSqlParams(this.dbType, sql, paramMapList);
        querySql.setSqlType(sqlType);
        querySql.setStreaming(consumer);
        this.jdbcExecutor.queryList(querySql);
    }

    /*********************************batch processing*********************************/
    public Integer batchUpdate(String sql, List<Map<String, Object>> paramMap) {
        SQL batchSQL = SQLParse.parseSqlParams(this.dbType, sql, paramMap);
        return this.jdbcExecutor.batchModify(batchSQL);
    }

    @Override
    public List<Id> batchInsert(String sql, List<Map<String, Object>> paramMap) {
        SQL pss = SQLParse.parseSqlParams(this.dbType, sql, paramMap);
        return this.jdbcExecutor.batchSave(pss);
    }

    @Override
    public void setBriefContext(BriefContext briefContext) {
        smartBriefContext = (SmartBriefContext) briefContext;
    }
}
