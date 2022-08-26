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
import com.javaoffers.batis.modelhelper.fun.crud.insert.OneInsertCol;
import com.javaoffers.batis.modelhelper.fun.crud.insert.OneInsertFun;

import java.util.Collection;
import java.util.List;

/**
 * Insert implementation
 * @author create by cmj
 */
public class InsertFunImpl<M> implements InsertFun<M, GetterFun<M,Object>,Object> {

    private Class<M> mClass;
    /**
     * save insert field.
     */
    private LinkedConditions<Condition> conditions = new LinkedConditions<>();

    private OneInsertFun oneInsertFun ;

    private MoreInsertFun moreInsertFun ;

    public InsertFunImpl(Class<M> mClass) {
        this.mClass = mClass;
        conditions.add(new InsertIntoCondition(mClass));
        oneInsertFun = new OneInsertFunImpl(mClass, conditions);
        moreInsertFun = new MoreInsertFunImpl(mClass, conditions);
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(M model) {
        moreInsertFun.colAll(model);
        return moreInsertFun;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, M model) {
        moreInsertFun.colAll(condition,model);
        return moreInsertFun;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(M... models) {
        moreInsertFun.colAll(models);
        return moreInsertFun;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, M... models) {
        moreInsertFun.colAll(condition, models);
        return moreInsertFun;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(Collection<M> models) {
        moreInsertFun.colAll(models);
        return moreInsertFun;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, Collection<M> models) {
        moreInsertFun.colAll(condition,models);
        return moreInsertFun;
    }

    @Override
    public OneInsertFun<M, GetterFun<M, Object>, Object> col(GetterFun<M, Object> col, Object value) {
        oneInsertFun.col(col,value);
        return oneInsertFun;
    }

    @Override
    public OneInsertFun<M, GetterFun<M, Object>, Object> col(boolean condition, GetterFun<M, Object> col, Object value) {
        oneInsertFun.col(condition,col,value);
        return oneInsertFun;
    }

}
