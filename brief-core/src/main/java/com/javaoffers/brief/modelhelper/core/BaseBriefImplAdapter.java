package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextAware;
import com.javaoffers.brief.modelhelper.context.SmartBriefContext;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorChain;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.filter.JqlMetaInfo;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.fun.condition.where.LimitWordCondition;
import com.javaoffers.brief.modelhelper.utils.SQLType;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @description: 包装代理， 没有用jdk代理实现是因为性能问题. 通过该类来扩展额外的功能。
 * @author: create by cmj on 2023/5/28 20:31
 */
public class BaseBriefImplAdapter<T, ID> implements BriefContextAware {

    private static volatile SmartBriefContext smartBriefContext;

    private BaseBrief baseBrief;

    private LimitWordCondition limit;

    private Class modelClass;

    public static <T, ID> BaseBriefImplAdapter getInstance(HeadCondition headCondition) {

        BaseBriefImpl baseBrief = new BaseBriefImpl(headCondition.getDataSource(), headCondition.getModelClass());
        BaseBriefImplAdapter adapter = new BaseBriefImplAdapter(baseBrief);
        adapter.modelClass = headCondition.getModelClass();
        adapter.limit = headCondition.getLimitWordCondition();
        return adapter;
    }

    public BaseBriefImplAdapter(BaseBrief baseBrief) {
        this.baseBrief = baseBrief;
    }
    
    private <R> R doProxy(JqlMetaInfo jqlMetaInfo, Function<JqlMetaInfo, R> supplier) {
        List<JqlExecutorFilter> jqlExecutorFilters = smartBriefContext.getJqlExecutorFilters();
        JqlExecutorChain<R> jqlExecutorChain = new JqlExecutorChain(supplier,
                jqlExecutorFilters, jqlMetaInfo, this.modelClass);
        return jqlExecutorChain.doChain();
    }

    public List<Id> batchInsert(BaseSQLStatement sqlStatement) {
        return doProxy(new JqlMetaInfo(sqlStatement), (jmi) -> {
            List<Map<String, Object>> params = jmi.getParams();
            return baseBrief.batchInsert(jmi.getSql(), params);
        });
    }

    public Integer batchUpdate(BaseSQLStatement sqlStatement) {
        return doProxy(new JqlMetaInfo(sqlStatement), (jmi) -> baseBrief.batchUpdate(jmi.getSql(), jmi.getParams()));
    }


    public int deleteData(BaseSQLStatement sqlStatement) {
        return doProxy(new JqlMetaInfo(sqlStatement), (jmi) -> {
            return baseBrief.deleteData(jmi.getSql(), jmi.getParam());
        });
    }

    public int updateData(BaseSQLStatement sqlStatement) {
        return doProxy(new JqlMetaInfo(sqlStatement), (jmi) -> {
            return baseBrief.updateData(jmi.getSql(), jmi.getParam());
        });
    }


    public List<T> queryData(BaseSQLStatement sqlStatement) {
        return doProxy(new JqlMetaInfo(sqlStatement), (jmi) -> {
            return baseBrief.queryData(jmi.getSql(), jmi.getParam());
        });
    }

    public int queryStream(BaseSQLStatement sqlStatement, Consumer<T> consumer) {
        return doProxy(new JqlMetaInfo(sqlStatement, consumer), (jmi) -> {
            return baseBrief.queryStream(jmi.getSql(), jmi.getParam(), jmi.getConsumer());
        });
    }


    public List<Object> nativeData(BaseSQLStatement sqlStatement, SQLType sqlType) {
        return doProxy(new JqlMetaInfo(sqlStatement,sqlType),
                (jmi) -> baseBrief.nativeData(jmi.getSql(), jmi.getParam(), jmi.getSqlType()));
    }

    public void nativeData(BaseSQLStatement sqlStatement, SQLType sqlType, Consumer<T> consumer) {
        doProxy(new JqlMetaInfo(sqlStatement, sqlType, consumer),
                (jmi) -> {
                    baseBrief.nativeData(jmi.getSql(), jmi.getParam(), jmi.getSqlType(), jmi.getConsumer());
                    return 0;
                });
    }

    public void setBriefContext(BriefContext briefContext) {
        smartBriefContext = (SmartBriefContext) briefContext;
    }

    public BaseBrief getOrgBaseBrief() {
        return this.baseBrief;
    }
}
