package com.javaoffers.brief.modelhelper.parse;

import com.javaoffers.brief.modelhelper.jdbc.ResultSetExecutor;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @Description: 解析model
 * @Auther: create by cmj on 2021/12/7 19:57
 */
public interface RealtimeModelParse  {

    /**
     * 模型解析
     * @param clazz
     * @param rs
     * @param <E>
     * @return void
     */
    public <E> List<E> converterResultSet2ModelForJoinSelect(Class<E> clazz, ResultSetExecutor rs);

    /**
     * 模型解析
     * @param clazz
     * @param rs
     * @param <E>
     * @return void
     */
    public <E> List<E> converterResultSet2ModelForNormalSelect(Class<E> clazz, ResultSetExecutor rs);


}
