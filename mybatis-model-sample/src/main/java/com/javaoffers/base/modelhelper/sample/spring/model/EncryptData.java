package com.javaoffers.base.modelhelper.sample.spring.model;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.anno.BaseUnique;
import lombok.Data;

@Data
@BaseModel
public class EncryptData {

    @BaseUnique
    private Integer id;

    private String encryptNum;
}
