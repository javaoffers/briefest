package com.javaoffers.batis.modelhelper.convert;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/7 19:53
 */
public interface Convert<T,V> {
    /**
     * 将 T 转换为V
     * @param t
     * @return
     */
     V convert (T t);


}
