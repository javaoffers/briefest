package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.crud.impl.SmartSelectFunImpl;

public interface SelectFun<M, C extends GetterFun<M, Object>, V> extends BaseSelectFun<M,C,V,SmartSelectFunImpl<M,C,V>> {

    /**
     * select distinct xxx
     * @return
     */
    SmartSelectFunImpl<M,C,V> distinct();

    /**
     * from table suffix.
     * sample: suffix = 'FORCE INDEX (idx_xx)'.
     * from table FORCE INDEX (idx_xx)
     * @param suffix
     * @return
     */
    SelectFun<M,C,V> tableSuffix(String suffix);

    /**
     * from suffix table .
     * sample: suffix = '(select xx from order)'.
     *  from (select xx from order) table
     * @param prefix
     * @return
     */
    SelectFun<M,C,V> tablePrefix(String prefix);

}
