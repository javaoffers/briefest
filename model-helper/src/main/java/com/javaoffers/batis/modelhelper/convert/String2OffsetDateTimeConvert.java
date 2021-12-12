package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.util.DateUtils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class String2OffsetDateTimeConvert extends AbstractConver<String, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(String s) {
        Date date = String2DateConvert.convert2(s);
        return DateUtils.parseOffsetDateTime(date);
    }
}
