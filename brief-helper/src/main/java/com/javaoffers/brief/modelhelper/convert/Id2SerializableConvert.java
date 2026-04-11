package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.Id;

import java.io.Serializable;
import java.math.BigInteger;

public class Id2SerializableConvert extends AbstractConver<Id, Serializable>{
    @Override
    public Serializable convert(Id id) {
        return id.value();
    }
}
