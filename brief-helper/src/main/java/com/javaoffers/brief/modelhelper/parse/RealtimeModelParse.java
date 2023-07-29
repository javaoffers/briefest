package com.javaoffers.brief.modelhelper.parse;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @Description:
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
    public <E> List<E> converterResultSet2Model(Class<E> clazz, ResultSet rs);

}
