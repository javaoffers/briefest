package com.javaoffers.brief.modelhelper.anno.derive.flag;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: Provide a version field and can be used for version updates.
 * @author: create by cmj on 2023/6/11 00:45
 */
public class Version extends Number {

    private volatile AtomicLong version;

    public Version(long initialValue) {
        version = new AtomicLong(initialValue);
    }

    public Version(Number initialValue) {
        version = new AtomicLong((initialValue == null ? 0 : initialValue.longValue()));
    }

    public Version() {
    }

    @Override
    public int intValue() {
        return version.intValue();
    }

    public long longValue() {
        return version.longValue();
    }

    @Override
    public float floatValue() {
        return version.floatValue();
    }

    @Override
    public double doubleValue() {
        return version.doubleValue();
    }

    public void incrementAndGet() {
        version.incrementAndGet();
    }

    public void decrementAndGet() {
        version.decrementAndGet();
    }

    public long get() {
        return version.get();
    }
}
