package com.javaoffers.batis.modelhelper.fun.crud.impl.insert;

import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
import com.javaoffers.batis.modelhelper.core.ConditionParse;
import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.core.SQLInfo;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.ColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.insert.InsertIntoCondition;
import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.crud.insert.InsertFun;
import com.javaoffers.batis.modelhelper.fun.crud.insert.MoreInsertFun;
import com.javaoffers.batis.modelhelper.fun.crud.insert.OneInsertFun;

import java.util.List;

/**
 * 插入实现
 * @author create by cmj
 */
public class InsertFunImpl<M> implements InsertFun<M, GetterFun<M,Object>,Object> {

    private Class<M> mClass;
    /**
     * 存放 查询字段
     */
    private LinkedConditions<Condition> conditions = new LinkedConditions<>();

    public InsertFunImpl(Class<M> mClass) {
        this.mClass = mClass;
        conditions.add(new InsertIntoCondition(mClass));
    }

    @Override
    public OneInsertFun<M, GetterFun<M, Object>, Object> col(GetterFun<M, Object> col, Object value) {
        conditions.add(new ColValueCondition(col,value));
        return this;
    }

    @Override
    public OneInsertFun<M, GetterFun<M, Object>, Object> col(boolean condition, GetterFun<M, Object> col, Object value) {
        if(condition){
            col(col,value);
        }
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(M model) {
        conditions.add(new InsertAllColValueCondition(mClass, model));
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, M model) {
        if(condition){
            colAll(model);
        }
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(M... models) {
        for(M m: models){
            colAll(m);
        }
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, M... models) {
        if(condition){
            colAll(models);
        }
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(List<M> models) {
        if(models!=null && models.size() > 0){
            models.forEach(model->{
                colAll(model);
            });
        }
        return this;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, List<M> models) {
        if(condition){
            colAll(models);
        }
        return this;
    }

    /**
     * 返回成功后的主键id, 多个id和插入model顺序保持一致
     * @return ids
     */
    @Override
    public List<Id> ex() {
        //conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //解析SQL select 并执行。
        SQLInfo sqlInfo = ConditionParse.conditionParse(conditions);
        System.out.println("SQL: "+sqlInfo.getSql());
        System.out.println("参数： "+sqlInfo.getParams());
        List<Id> list = BaseBatisImpl.baseBatis.batchInsert(sqlInfo.getSql(), sqlInfo.getParams());
        return list;
    }
}
