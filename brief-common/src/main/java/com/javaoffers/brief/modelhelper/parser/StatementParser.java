package com.javaoffers.brief.modelhelper.parser;

import com.javaoffers.brief.modelhelper.core.BaseSQLStatement;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.utils.DBTypeLabel;

import java.util.LinkedList;

/**
 * 将conditions解析为sql信息 {@code BaseSQLStatement}.
 * @author mingJie
 */
public interface StatementParser<T extends Condition, R extends BaseSQLStatement>  extends DBTypeLabel {

    /**
     * 解析生成数据片段.
     * @param conditions
     * @return
     */
    public R parse(LinkedList<T> conditions);

//    /**
//     * 对单个Condition进行处理 。在被添加到linked之前
//     * @param condition
//     * @return
//     */
//    public T transaction(T condition);

}
