package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.filter.ChainFilter;
import com.javaoffers.brief.modelhelper.filter.Filter;
import com.javaoffers.brief.modelhelper.filter.JqlChainFilter;
import com.javaoffers.brief.modelhelper.filter.SqlMetaInfo;
import com.javaoffers.brief.modelhelper.filter.impl.ChainFilterWrap;
import com.javaoffers.brief.modelhelper.filter.impl.ChainProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @description: 包装代理， 没有用jdk代理实现是因为性能问题. 通过该类来扩展额外的功能。
 * @author: create by cmj on 2023/5/28 20:31
 */
public class BaseBriefImplProxy<T, ID> implements BaseBrief<T> , BriefContextPostProcess {

    private static List<Filter> jqlChainFilterList = new ArrayList<>();

    private BaseBrief baseBrief;

    private Class modelClass;

    public BaseBriefImplProxy(BaseBrief baseBrief, Class modelClass) {
        this.baseBrief = baseBrief;
        this.modelClass = modelClass;
    }

    private <R> R doProxy(SqlMetaInfo sqlMetaInfo, Supplier<R> supplier){
        sqlMetaInfo.setBaseBrief(this);
        ChainFilterWrap<R, SqlMetaInfo> head = null;
        ChainFilterWrap<R, SqlMetaInfo> next = null;
        for(Filter jqlChainFilter : jqlChainFilterList){
            ChainFilterWrap<R, SqlMetaInfo> filterWrap = new ChainFilterWrap(sqlMetaInfo, (ChainFilter)jqlChainFilter);
            if(head == null){
                head = filterWrap;
                next = filterWrap;
            }else {
                next.setNextChain(filterWrap);
                next = filterWrap;
            }
        }

        JqlChainFilter last = objectSqlMetaInfoChain -> supplier.get();
        ChainFilterWrap<R, SqlMetaInfo> lastChainFilterSrap = new ChainFilterWrap(sqlMetaInfo, last);

        if(head == null){
            head = lastChainFilterSrap;
        }else{
            next.setNextChain(lastChainFilterSrap);
        }
        //拦截器
        ChainProcessor<R, SqlMetaInfo> chainProcessor = new ChainProcessor<R, SqlMetaInfo>(head);
        return chainProcessor.doChain();
    }

    @Override
    public int saveData(String sql) {
       return doProxy(new SqlMetaInfo(sql,modelClass), ()->{return baseBrief.saveData(sql);});
    }

    @Override
    public int saveData(String sql, Map<String, Object> map) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        return doProxy(new SqlMetaInfo(sql,maps, modelClass), ()->{return baseBrief.saveData(sql , map);});
    }

    @Override
    public int deleteData(String sql) {
        return doProxy(new SqlMetaInfo(sql,modelClass), ()->{return baseBrief.deleteData(sql);});
    }

    @Override
    public int deleteData(String sql, Map<String, Object> map) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        return doProxy(new SqlMetaInfo(sql,maps, modelClass), ()->{return baseBrief.deleteData(sql , map);});
    }

    @Override
    public int updateData(String sql) {
        return doProxy(new SqlMetaInfo(sql,modelClass), ()->{return baseBrief.updateData(sql);});
    }

    @Override
    public int updateData(String sql, Map<String, Object> map) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        return doProxy(new SqlMetaInfo(sql,maps, modelClass), ()->{return baseBrief.updateData(sql , map);});
    }

    @Override
    public List<T> queryData(String sql) {
        return doProxy(new SqlMetaInfo(sql,modelClass), ()->{return baseBrief.queryData(sql);});
    }

    @Override
    public List<T> queryData(String sql, Map<String, Object> map) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        return doProxy(new SqlMetaInfo(sql,maps, modelClass), ()->{return baseBrief.queryData(sql , map);});
    }

    @Override
    public Integer batchUpdate(String sql, List<Map<String, Object>> paramMap) {
        SqlMetaInfo sqlMetaInfo = new SqlMetaInfo(sql,paramMap, modelClass);
        return doProxy(sqlMetaInfo, ()-> baseBrief.batchUpdate(sql ,paramMap));
    }

    @Override
    public List<Id> batchInsert(String sql, List<Map<String, Object>> paramMap) {
        SqlMetaInfo sqlMetaInfo = new SqlMetaInfo(sql,paramMap, modelClass);
        return doProxy(sqlMetaInfo, ()-> baseBrief.batchInsert(sql ,paramMap));
    }

    @Override
    public void postProcess(BriefContext briefContext) {
        jqlChainFilterList = briefContext.getBriefProperties().getJqlFilters();
    }
}
