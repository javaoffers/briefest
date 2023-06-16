package com.javaoffers.brief.modelhelper.convert;

import java.math.BigDecimal;

/**
 * @Description: String2BigDecimalConvert
 * @Auther: create by cmj on 2021/12/10 12:59
 */
public class String2BigDecimalConvert extends AbstractConver<String, BigDecimal> {
    @Override
    public BigDecimal convert(String s) {
        return new BigDecimal(s);
    }
}
