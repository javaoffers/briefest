package com.javaoffers.brief.modelhelper.log;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextAware;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.context.BriefProperties;
import com.javaoffers.brief.modelhelper.context.SmartBriefContext;
import com.javaoffers.brief.modelhelper.context.SmartBriefProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JqlLogger implements BriefContextAware {
    public static Logger log = LoggerFactory.getLogger(JqlLogger.class);
    public static long time = -1;
    public static SmartBriefProperties briefProperties;

    public static void info(String info, Object param){
        log.info(info, param);
    }

    public static void infoSql(String info, Object param){
        if(briefProperties.isPrintSql())
            log.info(info, param);
    }

    public static void infoSqlCost(String info, Object... param){
        if(briefProperties.isPrintSqlCost())
            log.info(info, param);
    }

    @Override
    public void setBriefContext(BriefContext briefContext) {
        SmartBriefContext smartBriefContext = (SmartBriefContext)briefContext;
        briefProperties = smartBriefContext.getBriefProperties(SmartBriefProperties.class).get(0);
    }
}
