package com.javaoffers.batis.modelhelper.convert;

import java.sql.Date;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/10 13:11
 */
public class String2SQLDateConvert extends AbstractConver<String, Date> {
    static String2DateConvert dateConvert = new String2DateConvert();
    @Override
    public Date convert(String s) {
        java.util.Date convert = dateConvert.convert(s);
        return new Date(convert.getTime());
    }
}
