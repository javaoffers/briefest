package com.javaoffers.brief.modelhelper.core;

import org.apache.commons.lang3.time.DateFormatUtils;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mingJie
 */
public class AtoIncrementKey implements UniqueKeyGenerate<String> {
    static final String prefix = DateFormatUtils.format( new Date(), "yyMMddHHmmss");
    public static final AtomicLong key = new AtomicLong(1000000000000000000L);

    @Override
    public String generate() {
        return prefix + key.incrementAndGet();
    }
}
