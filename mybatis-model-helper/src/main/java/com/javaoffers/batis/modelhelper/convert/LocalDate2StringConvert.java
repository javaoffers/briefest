package com.javaoffers.batis.modelhelper.convert;

import java.time.LocalDate;

/**
 * 将localDate 转换为String
 */
public class LocalDate2StringConvert extends AbstractConver<LocalDate, String> {
    @Override
    public String convert(LocalDate localDate) {
        return localDate.toString();
    }
}
