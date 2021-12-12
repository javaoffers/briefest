package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.util.DateUtils;

import java.time.LocalDate;
import java.util.Date;

public class Date2LocalDateConvert extends AbstractConver<Date, LocalDate> {

    @Override
    public LocalDate convert(Date date) {
        return DateUtils.parseLocalDate(date);
    }
}
