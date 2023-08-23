package com.javaoffers.brief.modelhelper.log;

import com.javaoffers.brief.modelhelper.config.BriefProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JqlLogger {
    public static Logger log = LoggerFactory.getLogger(JqlLogger.class);
    public static long time = -1;
    static {
        time = BriefProperties.getSlowSqlLogTime();
    }
    public static void info(String info, Object param){
        log.info(info, param);
    }

    public static void infoSql(String info, Object param){
        if(BriefProperties.isIsPrintSql())
            log.info(info, param);
    }

    public static void infoSqlCost(String info, Object... param){
        if(BriefProperties.isIsPrintSqlCost())
            log.info(info, param);
    }
}
