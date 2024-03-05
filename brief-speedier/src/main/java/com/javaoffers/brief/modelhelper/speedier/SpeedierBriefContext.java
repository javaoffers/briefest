package com.javaoffers.brief.modelhelper.speedier;

import com.javaoffers.brief.modelhelper.context.SmartBriefContext;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.speedier.transaction.SpeedierTransactionManagement;

import javax.sql.DataSource;

/**
 * @author mingJie
 */
public class SpeedierBriefContext extends SmartBriefContext {

    SpeedierTransactionManagement speedierTransactionManagement;

    public SpeedierBriefContext(DataSource dataSource) {
        super(dataSource);
        this.speedierTransactionManagement = new SpeedierTransactionManagement(dataSource);
    }

    @Override
    public BriefMapper getBriefMapper(Class briefMapperClass) {
        return null;
    }

    public SpeedierTransactionManagement getTransactionManagement(){
         return this.speedierTransactionManagement;
    }
}
