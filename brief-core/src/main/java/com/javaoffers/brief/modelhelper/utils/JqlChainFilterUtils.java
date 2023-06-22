package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.brief.modelhelper.filter.JqlChainFilter;
import com.javaoffers.brief.modelhelper.filter.impl.ChainFilterWrap;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * create by cmj
 */
public class JqlChainFilterUtils {

    public static List<JqlChainFilter> getJqlChainFilter() {
        List<JqlChainFilter> jqlChainFilterList = new ArrayList<>();
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
        return jqlChainFilterList;
    }

}
