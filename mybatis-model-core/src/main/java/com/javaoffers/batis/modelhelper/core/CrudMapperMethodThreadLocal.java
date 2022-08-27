package com.javaoffers.batis.modelhelper.core;

/**
 * create by cmj
 */
public class CrudMapperMethodThreadLocal {

    private final static ThreadLocal<Class> excutorSelect = new InheritableThreadLocal<>();

    public static void addExcutorModel(Class clazz){
        excutorSelect.set(clazz);
    }

    public static void delExcutorModel(){
        excutorSelect.remove();
    }

    public static Class getExcutorModel(){
        return excutorSelect.get();
    }
}
