package com.javaoffers.brief.modelhelper.utils;

/**
 * @author mingJie
 */
@FunctionalInterface
public interface Setter<T, U> {

    void setter(T t, U value);
}
