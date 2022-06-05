package com.javaoffers.batis.modelhelper.fun;

import com.javaoffers.batis.modelhelper.core.BaseBatisImpl;
import com.javaoffers.batis.modelhelper.core.ConditionParse;
import com.javaoffers.batis.modelhelper.core.SQLInfo;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 执行sql 返回列表
 * @Auther: create by cmj on 2022/5/8 20:44
 */
public interface ExecutMoreFun<M> {

    /**
     * 获取条件.
     * @return
     */
    LinkedList<Condition> getConditions();
    /**
     * 执行sql 返回 List<M>
     * @return
     */
    default List<M> exs(){
        //conditions.stream().forEach(condition -> System.out.println(condition.toString()));
        //解析SQL select 并执行。
        SQLInfo sqlInfo = ConditionParse.conditionParse(getConditions());
        System.out.println(sqlInfo.getSql());
        System.out.println(sqlInfo.getParams());
        List list = BaseBatisImpl.baseBatis.queryDataForT4(sqlInfo.getSql(), sqlInfo.getParams().get(0), sqlInfo.getAClass());
        return list;
    }
}
