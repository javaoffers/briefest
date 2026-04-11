package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.Id;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Id2BIConvert extends AbstractConver<Id, BigInteger>{
    @Override
    public BigInteger convert(Id id) {
        return id.toBi();
    }
}
