package com.javaoffers.brief.modelhelper.log.slowsql;

import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class CostTimeLogger{
    public static final Properties properties = System.getProperties();
    public static Logger log = LoggerFactory.getLogger(CostTimeLogger.class);
    public static long time = -1;
    static {
        String mls = properties.getProperty(ConfigPropertiesConstants.SLOW_LOG_TIME);
        if(StringUtils.isNotBlank(mls)){
            time = Long.parseLong(mls.trim());
        }
    }
}