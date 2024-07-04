package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.util.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.LocalDateTime;
import java.util.Date;

public class LocalDateTime2StringConvert extends AbstractConver<LocalDateTime,String> {
    static Date2StringConvert date2StringConvert = new Date2StringConvert();
    @Override
    public String convert(LocalDateTime localDateTime) {
        return date2StringConvert.convert(DateUtils.toDate(localDateTime));
    }
}
