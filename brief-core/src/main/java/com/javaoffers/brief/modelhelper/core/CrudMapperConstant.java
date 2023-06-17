package com.javaoffers.brief.modelhelper.core;

/**
 * @Description: About BriefMapper interface method description
 * @Auther: create by cmj on 2022/5/3 13:31
 */
public enum  CrudMapperConstant {

    SELECT(1,"select"),

    UPDATE(2,"update"),

    DELETE(3,"delete"),

    INSERT(4,"insert"),

    GENERAL(5,"general"),
    ;


    private int code;
    private String methodName;

    CrudMapperConstant(int code, String methodName) {
        this.code = code;
        this.methodName = methodName;
    }

    public int getCode() {
        return code;
    }

    public String getMethodName() {
        return methodName;
    }
}
