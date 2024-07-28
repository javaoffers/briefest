package com.javaoffers.brief.modelhelper.fun.condition.select;

import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.Collections;
import java.util.Map;

/**
 * @Description: select from  语句 table 名称
 * @Auther: create by cmj on 2022/5/4 19:23
 */
public class SelectTableCondition implements Condition {

    private String fromTableName; //表名称

    private Class mClass;

    private TableInfo tableInfo;

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.SELECT_FROM;
    }

    @Override
    public String getSql() {
        return " " + ConditionTag.SELECT_FROM.getTag() +
                this.tableInfo.getBaseModel().fromView() +
                " " +fromTableName+
                " " ;
    }

    @Override
    public Map<String, Object> getParams() {
        return Collections.EMPTY_MAP;
    }

    public SelectTableCondition(String fromTableName) {
        this.fromTableName = fromTableName;
    }

    public SelectTableCondition(String fromTableName, Class mClass) {
        this.fromTableName = fromTableName;
        this.mClass = mClass;
        this.tableInfo = TableHelper.getTableInfo(this.mClass);
    }

    public String getFromTableName() {
        return fromTableName;
    }

    public String getFrontView(){
        return this.tableInfo.getBaseModel().frontView() + " ";
    }

    public Class getmClass() {
        return mClass;
    }

    public void setmClass(Class mClass) {
        this.mClass = mClass;
    }

    @Override
    public String toString() {
        return "SelectTableCondition{" +
                "fromTableName='" + fromTableName + '\'' +
                '}';
    }
}
