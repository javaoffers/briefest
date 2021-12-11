package com.javaoffers.base.batis.core;

import com.javaoffers.base.batis.convert.AbstractConver;
import com.javaoffers.base.batis.convert.Convert;
import com.javaoffers.base.batis.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * @Description: Selector represents and selector uses entry
 * @Auther: create by cmj on 2021/12/9 10:52
 */
public class ConvertRegisterSelectorDelegate {

    SelectorRegister applicationContext = new ModelApplicationContext();

    {
        Set<Class<? extends Convert>> converts = ReflectionUtils.getChildOfConvert();
        for (Class c : converts) {
            try {
                Constructor constructor = c.getConstructor();
                constructor.setAccessible(true);
                registerConvert( (AbstractConver) constructor.newInstance());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * registerConvert
     * @param convert
     */
    private void registerConvert( AbstractConver convert) {
        convert.register(applicationContext);
    }

    /**
     * selector
     * @return
     */
    private Convert selector(Class src,Class des) {
         return applicationContext.selector(new ConverDescriptor(src,des));
    }

    /**
     * 类型转换
     * @param des 要转换的目标类型
     * @param srcValue 原始值类型
     * @param <T>
     * @return
     */
    public <T> T converterObject(Class<T> des, Object srcValue) throws Exception{
        //基础类型转换为包装类型，如果存在基础类型
        des = baseClassUpgrade(des);
        Class src = baseClassUpgrade(srcValue.getClass());

        if(des == src || des.isAssignableFrom(src)){
            return (T) srcValue;
        }
        //选取 convert
        Convert selector = selector(srcValue.getClass(), des);
        //开始转换
        if(selector ==null){
            throw new ClassCastException("primitive type:" +srcValue.getClass().getName()+" target type: "+des.getName()+" nonexistent");
        }
        T convert = null;
        try {
            convert = (T) selector.convert(src.cast(srcValue));
        }catch (Exception e){
            e.printStackTrace();
        }
        return convert;
    }

    /**
     *        @see     java.lang.Boolean#TYPE
     *      * @see     java.lang.Character#TYPE
     *      * @see     java.lang.Byte#TYPE
     *      * @see     java.lang.Short#TYPE
     *      * @see     java.lang.Integer#TYPE
     *      * @see     java.lang.Long#TYPE
     *      * @see     java.lang.Float#TYPE
     *      * @see     java.lang.Double#TYPE
     *      * @see     java.lang.Void#TYPE
     * @param baseClass
     * @return
     */
    public Class baseClassUpgrade(Class baseClass){
        if(baseClass.isPrimitive()){
            if(boolean.class == baseClass){
                return Boolean.class;
            }
            if(char.class == baseClass){
                return Character.class;
            }
            if(byte.class == baseClass){
                return Byte.class;
            }
            if(short.class == baseClass){
                return Short.class;
            }
            if(int.class == baseClass){
                return Integer.class;
            }
            if(long.class == baseClass){
                return Long.class;
            }
            if(float.class == baseClass){
                return Float.class;
            }
            if(double.class == baseClass){
                return Double.class;
            }
            if(void.class == baseClass){
                return Void.class;
            }
        }
        return baseClass;
    }


}
