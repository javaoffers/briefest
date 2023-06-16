package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.util.DateUtils;

import java.time.OffsetDateTime;
import java.util.Date;

public class String2OffsetDateTimeConvert extends AbstractConver<String, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(String s) {
        Date date = String2DateConvert.convert2(s);
        return DateUtils.parseOffsetDateTime(date);
    }
}
