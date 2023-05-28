package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.batis.modelhelper.filter.JqlChainFilter;
import com.javaoffers.batis.modelhelper.filter.impl.ChainFilterWrap;
import com.javaoffers.batis.modelhelper.filter.impl.ChainProcessor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @description: 包装代理， 没有用jdk代理实现是因为性能问题. 通过该类来扩展额外的功能。
 * @author: create by cmj on 2023/5/28 20:31
 */
public class BaseBatisImplProxy<T, ID> implements BaseBatis<T,ID> {

    //拦截器
    ChainProcessor chainProcessor;

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
    }

    BaseBatis baseBatis;

    @Override
    public int saveData(String sql) {

        baseBatis.saveData(sql);

        return 0;
    }

    @Override
    public int saveData(String sql, Map<String, Object> map) {
        return 0;
    }

    @Override
    public int deleteData(String sql) {
        return 0;
    }

    @Override
    public int deleteData(String sql, Map<String, Object> map) {
        return 0;
    }

    @Override
    public int updateData(String sql) {
        return 0;
    }

    @Override
    public int updateData(String sql, Map<String, Object> map) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> queryData(String sql) {
        return null;
    }

    @Override
    public List<Map<String, Object>> queryData(String sql, Map<String, Object> map) {
        return null;
    }

    @Override
    public <E> List<E> queryDataForT(String sql, Class<E> clazz) {
        return null;
    }

    @Override
    public <E> List<E> queryDataForT4(String sql, Map<String, Object> map, Class<E> clazz) {
        return null;
    }

    @Override
    public Integer batchUpdate(String sql, List<Map<String, Object>> paramMap) {
        return null;
    }

    @Override
    public List<Serializable> batchInsert(String sql, List<Map<String, Object>> paramMap) {
        return null;
    }
}
