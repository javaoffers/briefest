package com.javaoffers.brief.modelhelper.convert;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/10 12:49
 */
public class String2CharConvert extends AbstractConver<String,Character> {
    @Override
    public Character convert(String s) {
        return new Character(s.toCharArray()[0]);
    }
}
