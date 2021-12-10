package com.javaoffers.base.batis.convert;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/10 13:02
 */
public class String2DateConvert extends AbstractConver<String, Date> {

    /**
     *    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
     * 	   private static final String DATETIME_PATTERN_TIME = "yyyyMMddHHmmssSS";
     * 	     //2018-01-17 15:17:14.0  （用el表达式在jsp页面中取date值时会出现这种格式）
     * 	    private static final String DATETIME_PATTERN_ENGLISH = "yyyy-MM-dd HH:mm:ss.s";
     *
     * 	     private static final String DATE_PATTERN = "yyyy-MM-dd";
     *
     * 	     private static final String MONTH_PATTERN = "yyyy-MM";
     */
    static String[] fs = new String[]{
             "yyyy-MM-dd HH:mm:ss"
            ,"yyyy-MM-ddHH:mm:ss"
            ,"yyyyMMddHHmmssSS"
            ,"yyyy-MM-dd HH:mm:ss.s"
            ,"yyyy-MM-ddHH:mm:ss.s"
            ,"yyyy-MM-dd"
            ,"yyyy-MM"
            ,"yyyy"
            ,"yyyy-MM-dd HH-mm-ss:SSS"
            ,"yyyy年MM月dd日,E,HH:mm:ss"
            ,"yyyy年MM月dd日 HH:mm:ss"
            ,"yyyy年MM月dd日HH:mm:ss"
            ,"yyyy年MM月dd日"
    };

    @Override
    public Date convert(String s) {
        Date date = null;
        try {
            date = DateUtils.parseDate(s, fs);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    protected static Date convert2(String s){
        Date date = null;
        try {
            date = DateUtils.parseDate(s, fs);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
