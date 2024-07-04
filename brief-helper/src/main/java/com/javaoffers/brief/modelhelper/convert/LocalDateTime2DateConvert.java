package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.util.DateUtils;

import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTime2DateConvert extends AbstractConver<LocalDateTime,Date> {

    @Override
    public Date convert(LocalDateTime localDateTime) {
        return DateUtils.toDate(localDateTime);
    }
}
