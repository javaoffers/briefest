package com.javaoffers.batis.modelhelper.fun.crud.update;

import com.javaoffers.batis.modelhelper.fun.ExecutOneFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereModifyFun;

import java.util.Collection;

/**
 * batch update
 * @author cmj
 */
public interface MoreUpdateFun <M, C extends GetterFun<M, Object>, V>  {


    /**
     * update all Col . include null value
     * @param model
     * @return
     */
    PrepareWhereModifyFun<M,C,V> colAll(M model);

    /**
     * update all Col . include null value
     * @param condition  true will update
     * @param model allCol
     * @return
     */
    PrepareWhereModifyFun<M,C,V> colAll(boolean condition , M model);


}
