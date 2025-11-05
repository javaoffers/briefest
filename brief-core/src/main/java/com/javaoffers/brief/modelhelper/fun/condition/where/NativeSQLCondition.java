package com.javaoffers.brief.modelhelper.fun.condition.where;

import com.javaoffers.brief.modelhelper.fun.condition.IgnoreAndOrWordCondition;

import java.util.Map;

public class NativeSQLCondition extends CondSQLCondition implements IgnoreAndOrWordCondition {

    public NativeSQLCondition(String sql, Map<String, Object> params) {
        super(sql, params);
        cleanAndOrTag();
    }

    public NativeSQLCondition(String sql) {
        super(sql);
        cleanAndOrTag();
        setAndOrTag(" ");
    }

    @Override
    public void setAndOrTag(String andOrTag) {
        super.setAndOrTag(andOrTag);
    }
}

