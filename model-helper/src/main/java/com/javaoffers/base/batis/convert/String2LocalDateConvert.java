package com.javaoffers.base.batis.convert;

import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

/**
 * @Description: String2LocalDateConvert
 * @Auther: create by cmj on 2021/12/10 13:16
 */
public class String2LocalDateConvert extends AbstractConver<String, LocalDate> {

    @Override
    public LocalDate convert(String s) {
        Date date = String2DateConvert.convert2(s);
        return LocalDate.of(date.getYear(),date.getMonth()+1,date.getDate());
    }

}
