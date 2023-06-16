package com.javaoffers.brief.modelhelper.convert;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class Date2StringConvert extends AbstractConver<Date,String> {

    @Override
    public String convert(Date date) {
        return DateFormatUtils.format(date,"yyyy-MM-dd HH:mm:ss");
    }
}
