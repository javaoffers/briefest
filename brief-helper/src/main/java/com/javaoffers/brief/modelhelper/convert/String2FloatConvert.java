package com.javaoffers.brief.modelhelper.convert;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/10 12:57
 */
public class String2FloatConvert extends AbstractConver<String,Float> {

    @Override
    public Float convert(String s) {
        return Float.parseFloat(s);
    }
}
