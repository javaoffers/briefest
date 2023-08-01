package com.javaoffers.base.modelhelper.sample.model;

import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import lombok.Data;

/**
 * Used for testing automatic updates
 */
@Data
@BaseModel(value = "encrypt_data",autoUpdate = true)
public class EncryptDataAutoUpdate {

    @BaseUnique
    private Integer id;

    private String encryptNum;
}
