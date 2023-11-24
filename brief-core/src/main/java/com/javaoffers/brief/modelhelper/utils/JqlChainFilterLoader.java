package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.config.BriefProperties;
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
public class JqlChainFilterLoader {

    public static List<JqlChainFilter> getJqlChainFilter() {
        return (List)BriefProperties.getJqlFilters();
    }

}
