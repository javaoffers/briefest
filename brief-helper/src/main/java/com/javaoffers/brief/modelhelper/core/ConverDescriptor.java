package com.javaoffers.brief.modelhelper.core;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/8 13:00
 */

public class ConverDescriptor implements Descriptor{

    private Class src;
    private Class des;
    private String um;

    @Override
    public String getUniqueMark() {
        return um;
    }

    public ConverDescriptor(Class src, Class des) {
        this.src = src;
        this.des = des;
        this.um = src.getName()+"#"+des.getName();
    }


}
