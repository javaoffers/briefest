package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.Id;

import java.io.Serializable;

public class Id2ByteConvert extends AbstractConver<Id, Byte>{
    @Override
    public Byte convert(Id id) {
        return id.toByte();
    }
}
