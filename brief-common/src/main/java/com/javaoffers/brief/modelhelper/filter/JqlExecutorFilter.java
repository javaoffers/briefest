package com.javaoffers.brief.modelhelper.filter;

/**
 * @author mingJie
 */
public interface JqlExecutorFilter extends Filter<Object, JqlExecutorChain>, Comparable<JqlExecutorFilter>{
    default int compareTo(JqlExecutorFilter o){
        return 0;
    }
}
