package com.javaoffers.brief.modelhelper.fun.condition;

import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.Collections;
import java.util.Map;

/**
 * @Description: left join 语句 table 名称
 * @Auther: create by cmj on 2022/5/4 19:23
 */
public class JoinTableCondition implements Condition {

    private String joinTableName; //表名称

    private String tableSuffix = "";

    private String tablePrefix = "";

    private ConditionTag tag;

    @Override
    public ConditionTag getConditionTag() {
        return this.tag;
    }

    @Override
    public String getSql() {
        return getConditionTag().getTag() + tablePrefix + " " +
                joinTableName + " " + tableSuffix + " ";
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }

    public String getLeftJoinTableName() {
        return joinTableName;
    }

    @Override
    public String toString() {
        return "LeftJoinTableCondition{" +
                "leftJoinTableName='" + joinTableName + '\'' +
                '}';
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    //join table not support front view
    public JoinTableCondition(Class joinTableClass, ConditionTag tag) {
        TableInfo tableInfo = TableHelper.getTableInfo(joinTableClass);
        BaseModel baseModel = tableInfo.getBaseModel();
        this.tableSuffix = baseModel.fromView();
        this.joinTableName = tableInfo.getTableName();
        this.tag = tag;
    }
}
