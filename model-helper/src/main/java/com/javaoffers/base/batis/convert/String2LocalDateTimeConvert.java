package com.javaoffers.base.batis.convert;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description: String2LocalDateTimeConvert
 * @Auther: create by cmj on 2021/12/10 13:33
 */
public class String2LocalDateTimeConvert extends AbstractConver<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String s) {
        Date date = String2DateConvert.convert2(s);
        return LocalDateTime.of(
                date.getYear()
                ,date.getMonth()+1
                ,date.getDate()
                ,date.getHours()
                ,date.getMinutes()
                ,date.getSeconds());
    }
}
