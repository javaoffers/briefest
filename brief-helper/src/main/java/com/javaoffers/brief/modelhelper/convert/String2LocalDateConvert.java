package com.javaoffers.brief.modelhelper.convert;

import java.time.LocalDate;
import java.util.Date;

/**
 * @Description: String2LocalDateConvert
 * @Auther: create by cmj on 2021/12/10 13:16
 */
public class String2LocalDateConvert extends AbstractConver<String, LocalDate> {

    @Override
    public LocalDate convert(String s) {
        Date date = String2DateConvert.convert2(s);
        return LocalDate.of(date.getYear()+1900,date.getMonth()+1,date.getDate());
    }

}
