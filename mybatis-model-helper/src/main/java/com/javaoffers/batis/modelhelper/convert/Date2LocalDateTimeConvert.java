package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.util.DateUtils;

import java.time.LocalDateTime;
import java.util.Date;

public class Date2LocalDateTimeConvert extends AbstractConver<Date, LocalDateTime> {
    @Override
    public LocalDateTime convert(Date date) {
        return DateUtils.parseLocalDateTime(date);
    }
}
