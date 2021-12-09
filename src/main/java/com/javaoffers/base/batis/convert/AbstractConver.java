package com.javaoffers.base.batis.convert;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/8 13:15
 */
public abstract  class AbstractConver<T,V> implements Convert<T,V>{

    static final String DEFAULT_STRING = "";
    static final int DEFAULT_INT = 0;
    static final long DEFAULT_LONG = 0L;
    static final double DEFAULT_DOUBLE = 0.0D;
    static final float DEFAULT_FLOAT = 0.0F;
    static final boolean DEFAULT_BOOLEAN = Boolean.FALSE;


}
