package com.javaoffers.brief.modelhelper.convert;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/10 12:58
 */
public class String2DoubleConvert extends AbstractConver<String,Double> {
    @Override
    public Double convert(String s) {
        return Double.parseDouble(s);
    }
}
