package com.javaoffers.brief.modelhelper.core.gkey;

import com.javaoffers.brief.modelhelper.core.UniqueKeyGenerate;

import java.util.UUID;

/**
 * @author mingJie
 */
public class UUIDKey implements UniqueKeyGenerate<String> {
    @Override
    public String generate() {
      return UUID.randomUUID().toString();
    }
}
