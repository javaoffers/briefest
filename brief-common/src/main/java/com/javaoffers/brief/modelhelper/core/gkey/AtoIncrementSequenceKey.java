package com.javaoffers.brief.modelhelper.core.gkey;

import com.javaoffers.brief.modelhelper.core.UniqueKeyGenerate;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 自增, 长度27位, nowTime(固定不变11位) + increment(原子自增 16位.).
 * @author mingJie
 */
public class AtoIncrementSequenceKey implements UniqueKeyGenerate<String> {
    static final String prefix = Long.toHexString(new Date().getTime());
    public static final AtomicLong key = new AtomicLong(1000000000000000000L);

    @Override
    public String generate() {
        return prefix + Long.toHexString(key.incrementAndGet());
    }
}
