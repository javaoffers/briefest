package com.javaoffers.batis.modelhelper.convert;

public class Boolean2StringConvert extends AbstractConver<Boolean,String> {
    @Override
    public String convert(Boolean aBoolean) {

        return aBoolean.toString();
    }
}
