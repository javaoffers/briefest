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

    public static final String DATE_FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_yyyyMMddHHmmss2 = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_yyyyMMddHHmmss3 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_yyyyMMddHHmmss4 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_yyyyMMddHHmmss5 = "yyyyMMddHHmmssSSS";
    public static final String DATE_FORMAT_yyyyMMddHHmmss6 = "yyyy/MM/dd HH:mm:ss.SSS";
    public static final String DATE_FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String DATE_FORMAT_yyyy_MM_dd2 = "yyyyMMdd";
    public static final String DATE_FORMAT_yyyy_MM_dd3 = "yyyy/MM/dd";
    public static final String DATE_FORMAT_yyyy_MM_dd4 = "yyyy_MM_dd";
    public static final String DATE_FORMAT_yyyy_MM_dd5 = "yyyyMMddHHmm";
    public static final String DATE_FORMAT_yyyy_MM_dd6 = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_dd_MMM_yyyy1 = "dd MMM yyyy";
    public static final String DATE_FORMAT_dd_MMM_yyyy2 = "dd-MM-yyyy";
    public static final String DATE_FORMAT_dd_MMM_yyyy3 = "dd/MM/yyyy";
    public static final String DATE_FORMAT_dd_MM_yyyy_HHmmss1 = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_FORMAT_MM_dd_yyyy = "MM/dd/yyyy";
    public static final String DATE_FORMAT_HH_mm_ss = "HHmmss";
    public static final String DATE_FORMAT_HH_mm_ss_SSS = "HHmmssSSS";
    public static final String DATE_FORMAT_MM_dd = "MMdd";

    /**
     *  format date
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
            ,DATE_FORMAT_yyyyMMddHHmmss
            ,DATE_FORMAT_yyyyMMddHHmmss2
            ,DATE_FORMAT_yyyyMMddHHmmss3
            ,DATE_FORMAT_yyyyMMddHHmmss4
            ,DATE_FORMAT_yyyyMMddHHmmss5
            ,DATE_FORMAT_yyyyMMddHHmmss6
            ,DATE_FORMAT_yyyy_MM_dd
            ,DATE_FORMAT_yyyy_MM_dd2
            ,DATE_FORMAT_yyyy_MM_dd3
            ,DATE_FORMAT_yyyy_MM_dd4
            ,DATE_FORMAT_yyyy_MM_dd5
            ,DATE_FORMAT_yyyy_MM_dd6
            ,DATE_FORMAT_dd_MMM_yyyy1
            ,DATE_FORMAT_dd_MMM_yyyy2
            ,DATE_FORMAT_dd_MMM_yyyy3
            ,DATE_FORMAT_dd_MM_yyyy_HHmmss1
            ,DATE_FORMAT_MM_dd_yyyy
            ,DATE_FORMAT_HH_mm_ss
            ,DATE_FORMAT_HH_mm_ss_SSS
            ,DATE_FORMAT_MM_dd

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
