package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ExecutFun;

/**
 * @Description:
 * @Auther: create by cmj on 2022/6/5 18:18
 */
public interface HavingPendingFun<M,C, V, R extends HavingFun<M,C, V,?>> extends ExecutFun<M>,
        OrderFun<M,C,V,HavingPendingFun<M,C,V,R>>
     {
    HavingFun<M,C,V, R> having();

    HavingPendingFun<M,C,V,R> limitPage(int pageNum, int size);

}
