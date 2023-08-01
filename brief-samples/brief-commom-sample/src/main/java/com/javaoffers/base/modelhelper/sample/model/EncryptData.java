package com.javaoffers.base.modelhelper.sample.model;

import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import lombok.Data;

@Data
@BaseModel
public class EncryptData {

    @BaseUnique
    private Integer id;

    private String encryptNum;
}
