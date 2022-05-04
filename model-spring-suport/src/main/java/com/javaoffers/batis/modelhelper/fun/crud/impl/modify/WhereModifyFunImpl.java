package com.javaoffers.batis.modelhelper.fun.crud.impl.modify;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ExecutFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import com.javaoffers.batis.modelhelper.fun.crud.impl.WhereFunImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 更新条件
 * @Auther: create by cmj on 2022/5/4 20:42
 */
public class WhereModifyFunImpl<M,V>  implements WhereFun<M, GetterFun,V, WhereModifyFunImpl<M,V>> , ExecutFun<Integer> {

    private LinkedList<Condition> conditions;


    private WhereFunImpl whereFun;

    /**静态**/
    private static JdbcTemplate jdbcTemplate;

    public WhereModifyFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.whereFun = new WhereFunImpl(this.conditions);
    }

    /**
     * 初始化使用 init
     * @param jdbcTemplate
     */
    public WhereModifyFunImpl (JdbcTemplate jdbcTemplate){
        if(jdbcTemplate != null){
            WhereModifyFunImpl.jdbcTemplate = jdbcTemplate;
        }
    }


    @Override
    public Integer ex() {
        return 0;
    }

    @Override
    public List<Integer> exs() {
        return Arrays.asList(ex());
    }

    @Override
    public WhereModifyFunImpl<M, V> or() {
        return null;
    }

    @Override
    public WhereModifyFunImpl<M, V> eq(GetterFun col, V value) {
        return null;
    }

    @Override
    public WhereModifyFunImpl<M, V> ueq(GetterFun col, V value) {
        return null;
    }

    @Override
    public WhereModifyFunImpl<M, V> gt(GetterFun col, V value) {
        return null;
    }

    @Override
    public WhereModifyFunImpl<M, V> lt(GetterFun col, V value) {
        return null;
    }

    @Override
    public WhereModifyFunImpl<M, V> gtEq(GetterFun col, V value) {
        return null;
    }

    @Override
    public WhereModifyFunImpl<M, V> ltEq(GetterFun col, V value) {
        return null;
    }

    @Override
    public WhereModifyFunImpl<M, V> between(GetterFun col, V start, V end) {
        return null;
    }

    @Override
    public WhereModifyFunImpl<M, V> like(GetterFun col, V value) {
        return null;
    }

    @Override
    public WhereModifyFunImpl<M, V> in(GetterFun col, V... values) {
        return null;
    }

    @Override
    public WhereModifyFunImpl<M, V> exists(String existsSql) {
        return null;
    }
}
