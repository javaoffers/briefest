package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.anno.derive.flag.Version;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class Number2VersionConvert extends AbstractConver<Number, Version> {

    @Override
    public Version convert(Number number) {
        return new Version(number);
    }
}
