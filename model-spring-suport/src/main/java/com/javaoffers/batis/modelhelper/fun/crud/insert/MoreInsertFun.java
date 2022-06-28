package com.javaoffers.batis.modelhelper.fun.crud.insert;

import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

import java.util.List;

/**
 * 多插入.
 * @author create by cmj
 */
public interface MoreInsertFun<M, C extends GetterFun<M, Object>, V> extends ExecutOneFun<Long> {

    /**
     * 将 model 中不为空的值进行全部查询. (自动匹配数据库字段进行插入)
     *
     * @param model 值
     * @return this
     */
    MoreInsertFun<M, C, V> colAll(M model);

    /**
     * 将 model 中不为空的值进行全部查询. (自动匹配数据库字段进行插入)
     *
     * @param condition 如果为true则插入
     * @param model 值
     * @return this
     */
    MoreInsertFun<M, C, V> colAll(boolean condition, M model);

    /**
     * 将 model 中不为空的值进行全部查询. (自动匹配数据库字段进行插入)
     *
     * @param models 值
     * @return this
     */
    MoreInsertFun<M, C, V> colAll(List<M> models);

    /**
     * 将 model 中不为空的值进行全部查询. (自动匹配数据库字段进行插入)
     * @param condition 如果为true则插入
     * @param models 值
     * @return this
     */
    MoreInsertFun<M, C, V> colAll(boolean condition, List<M> models);
}
