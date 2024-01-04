package com.javaoffers.brief.modelhelper.log;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.context.BriefProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JqlLogger implements BriefContextPostProcess {
    public static Logger log = LoggerFactory.getLogger(JqlLogger.class);
    public static long time = -1;
    public static BriefContext briefContext;
    public static BriefProperties briefProperties;

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
    public void postProcess(BriefContext briefContext) {
        JqlLogger.briefContext = briefContext;
        JqlLogger.briefProperties = briefContext.getBriefProperties();
    }
}
