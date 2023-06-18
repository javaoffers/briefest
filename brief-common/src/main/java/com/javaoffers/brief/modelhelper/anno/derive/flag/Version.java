package com.javaoffers.brief.modelhelper.anno.derive.flag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: Provide a version field and can be used for version updates.
 * @author: create by cmj on 2023/6/11 00:45
 */
public class Version extends AtomicLong {

    public Version(long initialValue) {
        super(initialValue);
    }

    public Version(Number initialValue) {
        super(initialValue == null ? 0 : initialValue.longValue());
    }

    public Version() {
    }
}
