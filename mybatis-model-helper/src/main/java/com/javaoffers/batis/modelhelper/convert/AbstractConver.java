package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.core.ConverDescriptor;
import com.javaoffers.batis.modelhelper.core.Register;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/8 13:15
 */
public abstract  class AbstractConver<T,V> implements Convert<T,V> {

    static final String DEFAULT_STRING = "";
    static final int DEFAULT_INT = 0;
    static final long DEFAULT_LONG = 0L;
    static final double DEFAULT_DOUBLE = 0.0D;
    static final float DEFAULT_FLOAT = 0.0F;
    static final boolean DEFAULT_BOOLEAN = Boolean.FALSE;

    /**
     * 注册自己
     * @param register
     */
    public void register(Register register) {

        Class c = this.getClass();
        Type genericSuperclass = c.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericSuperclass;
            Type[] types = pt.getActualTypeArguments();
            if (types == null || types.length != 2) {
                return;
            }
            try {
                Class src = (Class) types[0];
                Class des = (Class) types[1];
                register.registerConvert(new ConverDescriptor(src, des), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
