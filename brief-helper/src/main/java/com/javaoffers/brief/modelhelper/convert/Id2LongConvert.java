package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.Id;

public class Id2LongConvert extends AbstractConver<Id, Long>{
    @Override
    public Long convert(Id id) {
        return id.toLong();
    }
}
