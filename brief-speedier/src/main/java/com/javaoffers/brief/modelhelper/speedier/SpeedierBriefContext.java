package com.javaoffers.brief.modelhelper.speedier;

import com.javaoffers.brief.modelhelper.context.SmartBriefContext;
import com.javaoffers.brief.modelhelper.context.SmartBriefProperties;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.mapper.SmartMapperProxy;
import com.javaoffers.brief.modelhelper.speedier.transaction.SpeedierTransactionManagement;
import com.javaoffers.brief.modelhelper.utils.JdkProxyUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mingJie
 */
public class SpeedierBriefContext extends SmartBriefContext {

    SpeedierTransactionManagement speedierTransactionManagement;

    public SpeedierBriefContext(DataSource dataSource) {
        this.speedierTransactionManagement = new SpeedierTransactionManagement(dataSource);
    }

    public SpeedierBriefContext(SmartBriefProperties smartBriefProperties, DataSource dataSource) {
        super(smartBriefProperties);
    }

    public SpeedierTransactionManagement getTransactionManagement(){
         return this.speedierTransactionManagement;
    }

    @Override
    public DataSource getDataSource() {
        return this.speedierTransactionManagement.getDataSource();
    }
}
