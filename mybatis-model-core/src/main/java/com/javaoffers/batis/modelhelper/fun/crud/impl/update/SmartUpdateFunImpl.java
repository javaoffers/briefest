package com.javaoffers.batis.modelhelper.fun.crud.impl.update;

import com.javaoffers.batis.modelhelper.core.LinkedConditions;
import com.javaoffers.batis.modelhelper.fun.Condition;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.HeadCondition;
import com.javaoffers.batis.modelhelper.fun.condition.update.UpdateAllColValueCondition;
import com.javaoffers.batis.modelhelper.fun.condition.update.UpdateCondtionMark;
import com.javaoffers.batis.modelhelper.fun.crud.update.*;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author create by cmj on 2022-06-29
 */
public class SmartUpdateFunImpl<M, C extends GetterFun<M, Object>, V> implements SmartUpdateFun<M,C,V> {

    private Class<M> mClass;

    private String tableName;
    /**
     * Store the query field
     */
    private LinkedConditions<Condition> conditions = new LinkedConditions<>();

    /**
     * Whether to ignore null values, if it is null then do not update. true: update null.
     */
    private boolean isUpdateNull = true;

    private PrepareWhereModifyFunImpl<M,C,V> prepareWhereModifyFun ;

    private OneUpdateFunImpl<M,C,V> oneUpdateFun;

    public SmartUpdateFunImpl(Class<M> mClass, boolean isUpdateNull, JdbcTemplate jdbcTemplate) {
        this.mClass = mClass;
        this.isUpdateNull = isUpdateNull;
        this.tableName = TableHelper.getTableName(mClass);
        this.conditions.add(new HeadCondition(jdbcTemplate));
        this.conditions.add(new UpdateCondtionMark(mClass));
        this.prepareWhereModifyFun = new PrepareWhereModifyFunImpl<M,C,V>(conditions, mClass);
        this.oneUpdateFun = new OneUpdateFunImpl<M,C,V>(conditions,mClass,isUpdateNull);
    }

    SmartUpdateFunImpl(Class<M> mClass,LinkedConditions<Condition> conditions, boolean isUpdateNull) {
        this.mClass = mClass;
        this.isUpdateNull = isUpdateNull;
        this.conditions = conditions;
        this.tableName = TableHelper.getTableName(mClass);
        this.conditions.add(new UpdateCondtionMark(mClass));
        this.prepareWhereModifyFun = new PrepareWhereModifyFunImpl<M,C,V>(conditions, mClass);
        this.oneUpdateFun = new OneUpdateFunImpl<M,C,V>(conditions,mClass,isUpdateNull);
    }

    @Override
    public OneUpdateFun<M, C, V> col(C col, V value) {
        this.oneUpdateFun.col(col,value);
        return this.oneUpdateFun;
    }

    @Override
    public OneUpdateFun<M, C, V> col(boolean condition, C col, V value) {
        this.oneUpdateFun.col(condition,col,value);
        return this.oneUpdateFun;
    }

    @Override
    public PrepareWhereModifyFun<M, C, V> colAll(M model) {
        this.conditions.add(new UpdateAllColValueCondition(isUpdateNull, mClass, model));
        return  this.prepareWhereModifyFun;
    }

    @Override
    public PrepareWhereModifyFun<M, C, V> colAll(boolean condition, M model) {
        if(condition){
            colAll(model);
        }
        return this.prepareWhereModifyFun;
    }
}
