package com.javaoffers.brief.modelhelper.filter;

/**
 * @author mingJie
 */
public interface JqlExecutorFilter extends Filter<Object, JqlExecutorChain>, Comparable<JqlExecutorFilter>{

    Integer MIN_ORDER = Integer.MIN_VALUE >> 2;

    Integer MAX_ORDER = Integer.MAX_VALUE >> 2;

    default int compareTo(JqlExecutorFilter o){
        return this.orderId() - o.orderId();
    }
    default int orderId(){
        return 0;
    }
}
