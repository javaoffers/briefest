package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.consistant.ModelConsistants;
import com.javaoffers.batis.modelhelper.core.ConverDescriptor;
import com.javaoffers.batis.modelhelper.core.Register;

/**
 *Number (java.lang)
 *     MutableFloat (org.apache.commons.lang3.mutable)
 *     Float (java.lang)
 *     MutableInt (org.apache.commons.lang3.mutable)
 *     Double (java.lang)
 *     Striped64 (java.util.concurrent.atomic)
 *         LongAdder (java.util.concurrent.atomic)
 *         LongAccumulator (java.util.concurrent.atomic)
 *         DoubleAdder (java.util.concurrent.atomic)
 *         DoubleAccumulator (java.util.concurrent.atomic)
 *     Integer (java.lang)
 *     MutableDouble (org.apache.commons.lang3.mutable)
 *     BigDecimal (java.math)
 *     AtomicLong (java.util.concurrent.atomic)
 *     Long (java.lang)
 *     AtomicInteger (java.util.concurrent.atomic)
 *     MutableLong (org.apache.commons.lang3.mutable)
 *     Short (java.lang)
 *     BigInteger (java.math)
 *     MutableShort (org.apache.commons.lang3.mutable)
 *     Fraction (org.apache.commons.lang3.math)
 *     Byte (java.lang)
 *     MutableByte (org.apache.commons.lang3.mutable)
 *
 * 以上类均可转换为String
 */
public class Number2StringConvert extends AbstractConver<Number,String> {

    @Override
    public String convert(Number number) {
        return number.toString();
    }

    @Override
    public void register(Register register) {
        Class[] baseNumberClass = ModelConsistants.baseNumberClass;
        for(Class src : baseNumberClass){
            register.registerConvert(new ConverDescriptor(src,String.class),this);
        }
    }
}
