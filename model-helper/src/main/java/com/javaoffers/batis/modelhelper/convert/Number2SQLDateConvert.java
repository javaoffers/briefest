package com.javaoffers.batis.modelhelper.convert;

import java.sql.Date;

public class Number2SQLDateConvert extends AbstractConver<Number, Date> {
    @Override
    public Date convert(Number number) {
        return new Date(number.longValue());
    }
}
