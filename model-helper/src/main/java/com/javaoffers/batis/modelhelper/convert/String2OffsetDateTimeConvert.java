package com.javaoffers.batis.modelhelper.convert;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class String2OffsetDateTimeConvert extends AbstractConver<String, OffsetDateTime> {
    private static  ZoneOffset offset = OffsetDateTime.now().getOffset();
    @Override
    public OffsetDateTime convert(String s) {

        Date date = String2DateConvert.convert2(s);
        return OffsetDateTime.of(LocalDateTime.of(date.getYear()+1900
                ,date.getMonth()+1
                ,date.getDate()
                ,date.getHours()
                ,date.getMinutes()
                ,date.getSeconds()),offset);
    }
}
