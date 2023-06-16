package com.javaoffers.brief.modelhelper.convert;

import java.time.LocalDateTime;
import java.util.Date;

public class Number2LocalDateTimeConvert extends AbstractConver<Number, LocalDateTime> {
    @Override
    public LocalDateTime convert(Number number) {
        Date date = new Date(number.longValue());
        return LocalDateTime.of(date.getYear()+1900
                ,date.getMonth()+1
                ,date.getDate()
                ,date.getHours()
                ,date.getMinutes()
                ,date.getSeconds());
    }
}
