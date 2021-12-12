package com.javaoffers.batis.modelhelper.convert;

import java.util.Date;

public class Number2DateConvert extends AbstractConver<Number, Date> {
    @Override
    public Date convert(Number number) {
        return new Date(number.longValue());
    }
}
