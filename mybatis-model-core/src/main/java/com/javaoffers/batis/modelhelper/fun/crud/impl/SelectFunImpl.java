package com.javaoffers.batis.modelhelper.fun.crud.impl;

import com.javaoffers.batis.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.fun.*;
import com.javaoffers.batis.modelhelper.fun.condition.*;
import com.javaoffers.batis.modelhelper.fun.crud.SelectFun;
import com.javaoffers.batis.modelhelper.fun.crud.SmartSelectFun;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Description:  以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 01:55
 */
public class SelectFunImpl<M> implements SelectFun<M,GetterFun<M,Object>,Object> {

    private Class<M> mClass;
    /**
     * 存放 查询字段
     */
    private LinkedConditions<Condition> conditions = new LinkedConditions<>();
    {
        this.conditions.beforeAdd((before,current)->{
            if(before == null){
                return;
            }else{
                if(before instanceof OrderWordCondition && current instanceof OrderWordCondition){
                    ((OrderWordCondition) current).asChild();
                }
                if(before instanceof LFCondition){
                    LFCondition rfWordCondition = (LFCondition) before;
                    current.andOrWord(word->{
                        return "";
                    });
                }
            }
        });
    }

    private SmartSelectFunImpl<M,GetterFun<M,Object>,Object> smartSelectFun ;

    public SelectFunImpl(Class<M> mClass) {
        this.conditions.add(new JdbcTemplateCondition(CrudMapperMethodThreadLocal.getExcutorJdbcTemplate()));
        this.conditions.add(new SelectTableCondition(TableHelper.getTableName(mClass), mClass));
        this.mClass = mClass;
        this.smartSelectFun = new SmartSelectFunImpl(mClass, conditions);
    }

    /**
     *  添加查询字段
     * @param cols
     * @return
     */
    @Override
    public SmartSelectFun<M, GetterFun<M,Object>, Object> col(GetterFun... cols) {
        this.smartSelectFun.col(cols);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, GetterFun<M, Object>... cols) {
        this.smartSelectFun.col(condition, cols);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(AggTag aggTag, GetterFun<M, Object>... cols) {

        this.smartSelectFun.col(aggTag,cols);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, AggTag aggTag, GetterFun<M, Object>... cols) {
        this. smartSelectFun.col(condition, aggTag, cols);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(AggTag aggTag, GetterFun<M, Object> col, String asName) {
        this.smartSelectFun.col(aggTag, col, asName);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, AggTag aggTag, GetterFun<M, Object> col, String asName) {
        this.smartSelectFun.col(condition,aggTag,col,asName);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M,Object>, Object> col(String... colSql) {
        this.smartSelectFun.col(colSql);
        return this.smartSelectFun;
    }

    @Override
    public SmartSelectFun<M, GetterFun<M, Object>, Object> col(boolean condition, String... colSql) {
        this.smartSelectFun.col(condition, colSql);
        return this.smartSelectFun;
    }

    /**
     * 添加所有字段
     * @return
     */
    @Override
    public SmartSelectFun<M, GetterFun<M,Object>, Object> colAll() {

        this.smartSelectFun.colAll();
        return this.smartSelectFun;
    }



}
