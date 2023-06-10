package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.batis.modelhelper.filter.Chain;
import com.javaoffers.batis.modelhelper.filter.JqlChainFilter;
import com.javaoffers.batis.modelhelper.filter.SqlMetaInfo;
import com.javaoffers.batis.modelhelper.filter.impl.ChainFilterWrap;
import com.javaoffers.batis.modelhelper.filter.impl.ChainProcessor;
import com.javaoffers.batis.modelhelper.utils.ReflectionUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @description: 包装代理， 没有用jdk代理实现是因为性能问题. 通过该类来扩展额外的功能。
 * @author: create by cmj on 2023/5/28 20:31
 */
public class BaseBatisImplProxy<T, ID> implements BaseBatis<T,ID> {

    static List<JqlChainFilter> jqlChainFilterList = new ArrayList<>();

    static {
        Properties properties = System.getProperties();
        Object o = properties.get(ConfigPropertiesConstants.JQL_FILTER);

        ChainFilterWrap chainFilterWrap = null;
        ChainFilterWrap headChainFilter = null;
        if(o != null){
            String jqlFilters = String.valueOf(o);
            String[] jqlFilterArray = jqlFilters.replaceAll(" ", "").split(",");
            for(String jqlFilterClassName : jqlFilterArray){
                try {
                    Class<?> jqlFilterClass = Class.forName(jqlFilterClassName);
                    JqlChainFilter jqlChainFilter = (JqlChainFilter)jqlFilterClass.newInstance();
                    jqlChainFilterList.add(jqlChainFilter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        Set<Class<? extends JqlChainFilter>> childs = ReflectionUtils.getChilds(JqlChainFilter.class);
        if(CollectionUtils.isNotEmpty(childs)){
            for(Class clazz : childs){
                try {
                    JqlChainFilter o1 = (JqlChainFilter)clazz.newInstance();
                    jqlChainFilterList.add(o1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    private BaseBatis baseBatis;

    private Class modelClass;

    public BaseBatisImplProxy(BaseBatis baseBatis, Class modelClass) {
        this.baseBatis = baseBatis;
        this.modelClass = modelClass;
    }

    private <R> R doProxy(SqlMetaInfo sqlMetaInfo, Supplier<R> supplier){
        ChainFilterWrap<R, SqlMetaInfo> head = null;
        ChainFilterWrap<R, SqlMetaInfo> next = null;
        for(JqlChainFilter jqlChainFilter : jqlChainFilterList){
            ChainFilterWrap<R, SqlMetaInfo> filterWrap = new ChainFilterWrap(sqlMetaInfo, jqlChainFilter);
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
       return doProxy(new SqlMetaInfo(sql,modelClass), ()->{return baseBatis.saveData(sql);});
    }

    @Override
    public int saveData(String sql, Map<String, Object> map) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        return doProxy(new SqlMetaInfo(sql,maps, modelClass), ()->{return baseBatis.saveData(sql , map);});
    }

    @Override
    public int deleteData(String sql) {
        return doProxy(new SqlMetaInfo(sql,modelClass), ()->{return baseBatis.deleteData(sql);});
    }

    @Override
    public int deleteData(String sql, Map<String, Object> map) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        return doProxy(new SqlMetaInfo(sql,maps, modelClass), ()->{return baseBatis.deleteData(sql , map);});
    }

    @Override
    public int updateData(String sql) {
        return doProxy(new SqlMetaInfo(sql,modelClass), ()->{return baseBatis.updateData(sql);});
    }

    @Override
    public int updateData(String sql, Map<String, Object> map) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        return doProxy(new SqlMetaInfo(sql,maps, modelClass), ()->{return baseBatis.updateData(sql , map);});
    }

    @Override
    public List<Map<String, Object>> queryData(String sql) {
        return doProxy(new SqlMetaInfo(sql,modelClass), ()->{return baseBatis.queryData(sql);});
    }

    @Override
    public List<Map<String, Object>> queryData(String sql, Map<String, Object> map) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        return doProxy(new SqlMetaInfo(sql,maps, modelClass), ()->{return baseBatis.queryData(sql , map);});
    }

    @Override
    public <E> List<E> queryDataForT(String sql, Class<E> clazz) {
        SqlMetaInfo sqlMetaInfo = new SqlMetaInfo(sql, clazz);
        return doProxy(sqlMetaInfo, ()-> baseBatis.queryDataForT(sql, clazz));
    }

    @Override
    public <E> List<E> queryDataForT4(String sql, Map<String, Object> map, Class<E> clazz) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        SqlMetaInfo sqlMetaInfo = new SqlMetaInfo(sql,maps, clazz);
        return doProxy(sqlMetaInfo, ()-> baseBatis.queryDataForT4(sql ,map, clazz));
    }

    @Override
    public Integer batchUpdate(String sql, List<Map<String, Object>> paramMap) {
        SqlMetaInfo sqlMetaInfo = new SqlMetaInfo(sql,paramMap, modelClass);
        return doProxy(sqlMetaInfo, ()-> baseBatis.batchUpdate(sql ,paramMap));
    }

    @Override
    public List<Serializable> batchInsert(String sql, List<Map<String, Object>> paramMap) {
        SqlMetaInfo sqlMetaInfo = new SqlMetaInfo(sql,paramMap, modelClass);
        return doProxy(sqlMetaInfo, ()-> baseBatis.batchInsert(sql ,paramMap));
    }
}
