package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.Id;

import java.math.BigDecimal;

public class Id2BdConvert extends AbstractConver<Id, BigDecimal>{
    @Override
    public BigDecimal convert(Id id) {
        return id.toBd();
    }
}
