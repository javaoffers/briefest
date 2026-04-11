package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.core.Id;

public class Id2StringConvert extends AbstractConver<Id, String>{
    @Override
    public String convert(Id id) {
        return id.toString();
    }
}
