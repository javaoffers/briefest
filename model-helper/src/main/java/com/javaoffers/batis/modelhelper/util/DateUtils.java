package com.javaoffers.batis.modelhelper.util;

import com.javaoffers.batis.modelhelper.convert.String2DateConvert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtils {

    private static ZoneOffset offset = OffsetDateTime.now().getOffset();

    public static LocalDateTime parseLocalDateTime(Date date){
        return  LocalDateTime.of(
                date.getYear()+1900
                ,date.getMonth()+1
                ,date.getDate()
                ,date.getHours()
                ,date.getMinutes()
                ,date.getSeconds()
        );
    }

    public static LocalDate parseLocalDate(Date date) {
        return LocalDate.of(date.getYear()+ 1900,date.getMonth()+1,date.getDate());
    }

    public static OffsetDateTime parseOffsetDateTime(Date date) {
        return OffsetDateTime.of(LocalDateTime.of(date.getYear()+1900
                ,date.getMonth()+1
                ,date.getDate()
                ,date.getHours()
                ,date.getMinutes()
                ,date.getSeconds()),offset);
    }

}
