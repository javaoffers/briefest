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

    //解析生成数据片段.
    public R parse(LinkedList<T> conditions);

}
