package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.util.DateUtils;

import java.time.OffsetDateTime;
import java.util.Date;

public class Date2OffsetDateTimeConvert extends AbstractConver<Date, OffsetDateTime> {
    @Override
    public OffsetDateTime convert(Date date) {
        return DateUtils.parseOffsetDateTime(date);
    }
}
