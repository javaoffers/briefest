package com.javaoffers.batis.modelhelper.fun.crud;

import com.javaoffers.batis.modelhelper.fun.ExecutFun;
import com.javaoffers.batis.modelhelper.fun.GetterFun;

import java.io.Serializable;

/**
 * @Description: on 拼接sql 条件
 * @Auther: create by cmj on 2022/5/2 00:56
 * sql: selct xx from a left join b on a.col = b.col
 */
public interface OnFun<M1, M2, V> extends
        FirstOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V>,
        SmartOnFun<M1, M2, GetterFun<M1, Object>, GetterFun<M2, Object>, V>,
        ExecutFun<M1> {


}
