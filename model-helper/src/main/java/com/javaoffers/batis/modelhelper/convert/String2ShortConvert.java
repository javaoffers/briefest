package com.javaoffers.batis.modelhelper.convert;

/**
 * @Description: String2ShortConvert
 * @Auther: create by cmj on 2021/12/10 12:52
 */
public class String2ShortConvert extends AbstractConver<String,Short> {
    @Override
    public Short convert(String s) {
        return Short.parseShort(s);
    }
}
