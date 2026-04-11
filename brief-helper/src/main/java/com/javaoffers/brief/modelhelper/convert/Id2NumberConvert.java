package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.Id;

import java.math.BigInteger;

public class Id2NumberConvert extends AbstractConver<Id, Number>{
    @Override
    public Number convert(Id id) {
        return id.toNumber();
    }
}
