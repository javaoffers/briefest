package com.javaoffers.batis.modelhelper.fun.crud.impl.modify;

import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.ExecutFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereModifyFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;
import com.javaoffers.batis.modelhelper.fun.crud.impl.WhereSelectFunImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 更新条件
 * @Auther: create by cmj on 2022/5/4 20:42
 */
public class WhereModifyFunImpl<M,V>  implements WhereModifyFun<M,V> {

    private LinkedList<Condition> conditions;


    private WhereSelectFunImpl whereFun;

    /**静态**/
    private static JdbcTemplate jdbcTemplate;

    public WhereModifyFunImpl(LinkedList<Condition> conditions) {
        this.conditions = conditions;
        this.whereFun = new WhereSelectFunImpl(this.conditions);
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
    public WhereModifyFun<M, V> or() {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> eq(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> ueq(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> gt(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> lt(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> gtEq(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> ltEq(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> between(GetterFun<M, V> col, V start, V end) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> like(GetterFun<M, V> col, V value) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> in(GetterFun<M, V> col, V... values) {
        return null;
    }

    @Override
    public WhereModifyFun<M, V> exists(String existsSql) {
        return null;
    }
}
