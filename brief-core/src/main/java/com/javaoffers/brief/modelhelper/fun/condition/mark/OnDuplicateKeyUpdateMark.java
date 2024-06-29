package com.javaoffers.brief.modelhelper.fun.condition.mark;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: create by cmj on 2022/11/19 12:30
 */
public  class OnDuplicateKeyUpdateMark implements Condition {

    private Class modelClass;

    public OnDuplicateKeyUpdateMark(Class modelClass) {
        this.modelClass = modelClass;
        TableInfo tableInfo = TableHelper.getTableInfo(this.modelClass);
        if(!tableInfo.getDbType().isSupportDuplicateModify()){
            throw new UnsupportedOperationException(tableInfo.getDbType().name() + " unsupported dupUpdate operation");
        }
    }

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.ON_DUPLICATE_KEY_UPDATE;
    }

    @Override
    public String getSql() {
        return getConditionTag().getTag();
    }

    @Override
    public Map<String, Object> getParams() {
        return new HashMap<>();
    }
}
