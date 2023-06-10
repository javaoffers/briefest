package com.javaoffers.batis.modelhelper.fun.crud.impl.insert;

import com.javaoffers.batis.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.HeadCondition;
import com.javaoffers.batis.modelhelper.fun.condition.insert.InsertIntoCondition;
import com.javaoffers.batis.modelhelper.fun.crud.insert.InsertFun;
import com.javaoffers.batis.modelhelper.fun.crud.insert.MoreInsertFun;
import com.javaoffers.batis.modelhelper.fun.crud.insert.OneInsertFun;
import com.javaoffers.batis.modelhelper.fun.crud.insert.SmartMoreInsertFun;

import java.util.Collection;

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

    private SmartMoreInsertFun moreInsertFun ;

    public InsertFunImpl(Class<M> mClass) {
        this.mClass = mClass;
        this.conditions.add(new HeadCondition(CrudMapperMethodThreadLocal.getExcutorJdbcTemplate(), mClass));
        this.conditions.add(new InsertIntoCondition(mClass));
        this.oneInsertFun = new OneInsertFunImpl(mClass, conditions);
        this.moreInsertFun = new SmartMoreInsertFunImpl(mClass, conditions);
    }

    @Override
    public SmartMoreInsertFun<M, GetterFun<M, Object>, Object> colAll(M model) {
        moreInsertFun.colAll(model);
        return moreInsertFun;
    }

    @Override
    public SmartMoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, M model) {
        moreInsertFun.colAll(condition,model);
        return moreInsertFun;
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(M... models) {
       return moreInsertFun.colAll(models);
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, M... models) {
        return moreInsertFun.colAll(condition, models);
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(Collection<M> models) {
        return  moreInsertFun.colAll(models);
    }

    @Override
    public MoreInsertFun<M, GetterFun<M, Object>, Object> colAll(boolean condition, Collection<M> models) {
        return moreInsertFun.colAll(condition,models);
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
