package com.javaoffers.base.modelhelper.sample.spring.model;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.anno.BaseUnique;

import java.util.Date;

@BaseModel

public class User {

    @BaseUnique
    private String id;

    private String name;

    private long brithday;

}
