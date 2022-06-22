package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.fun.crud.impl.SelectFunImpl;

/**
 * create by cmj on 2022-06-22 23:37:17
 */
public class CrudMapperMethodExcutor {

    private final static ThreadLocal<Class> excutorSelect = new InheritableThreadLocal<>();

    public static SelectFunImpl select(){
        return new SelectFunImpl(excutorSelect.get());
    }

    public static void addExcutorSelect(Class clazz){
        excutorSelect.set(clazz);
    }

    public static void delExcutorSelect(Class clazz){
        excutorSelect.remove();
    }


}
