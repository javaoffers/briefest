package com.javaoffers.batis.modelhelper.utils;

import net.bytebuddy.ByteBuddy;

/**
 * byteBuddy工具
 * @author create by cmj on 2022-06-22 00:24:42
 */
public class ByteBuddyUtils {

    /**
     * 根据class生成子类，
     * @param clazz 可以为接口class
     * @return 子类
     */
    public static Object makeObject(Class clazz){
        Class<?> dynamicType = new ByteBuddy()
                .subclass(clazz) //定义谁的子类
                .make()
                .load(clazz.getClassLoader())
                .getLoaded();
        try {
            return dynamicType.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
