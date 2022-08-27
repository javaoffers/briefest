package com.javaoffers.batis.modelhelper.convert;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/10 12:56
 */
public class String2LongConvert extends AbstractConver<String,Long> {
    @Override
    public Long convert(String s) {
        return Long.parseLong(s);
    }
}
