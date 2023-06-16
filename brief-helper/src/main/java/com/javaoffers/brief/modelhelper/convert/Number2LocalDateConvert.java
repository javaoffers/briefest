package com.javaoffers.brief.modelhelper.convert;

import java.time.LocalDate;
import java.util.Date;

public class Number2LocalDateConvert extends AbstractConver<Number, LocalDate> {
    @Override
    public LocalDate convert(Number number) {
        Date date = new Date(number.longValue());
        return LocalDate.of(date.getYear()+ 1900,date.getMonth()+1,date.getDate());
    }
}
