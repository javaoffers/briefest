package com.javaoffers.batis.modelhelper.core;

/**
 * create by cmj
 */
public class CrudMapperMethodThreadLocal {

    private final static ThreadLocal<Class> excutorSelect = new InheritableThreadLocal<>();

    public static void addExcutorSelect(Class clazz){
        excutorSelect.set(clazz);
    }

    public static void delExcutorSelect(){
        excutorSelect.remove();
    }

    public static Class getExcutorSelect(){
        return excutorSelect.get();
    }
}
