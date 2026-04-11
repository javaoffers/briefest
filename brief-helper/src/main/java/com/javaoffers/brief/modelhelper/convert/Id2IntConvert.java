package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.Id;

public class Id2IntConvert extends AbstractConver<Id, Integer>{
    @Override
    public Integer convert(Id id) {
        return id.toInt();
    }
}
