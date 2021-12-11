package com.javaoffers.batis.modelhelper.convert;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/10 12:18
 */
public class String2IntegerConvert extends AbstractConver<String,Integer> {
    @Override
    public Integer convert(String s) {
        return Integer.parseInt(s);
    }
}
