package com.javaoffers.batis.modelhelper.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author create by cmj
 */
public class IdImpl implements Id {

    private static final ConvertRegisterSelectorDelegate convert = ConvertRegisterSelectorDelegate.convert;

    public static final IdImpl EMPTY_ID = new IdImpl(Integer.MIN_VALUE);

    Serializable srcValue;

    @Override
    public int toByte() {
        return convert.converterObject(byte.class, srcValue);
    }

    @Override
    public int toInt() {
        return convert.converterObject(int.class, srcValue);
    }

    @Override
    public long toLong() {
        return convert.converterObject(long.class, srcValue);
    }

    @Override
    public BigDecimal toBd() {
        return convert.converterObject(BigDecimal.class, srcValue);
    }

    @Override
    public BigInteger toBi() {
        return convert.converterObject(BigInteger.class, srcValue);
    }

    @Override
    public Number toNumber() {
        return convert.converterObject(Double.class, srcValue);
    }

    @Override
    public Serializable value() {
        return srcValue;
    }

    public IdImpl(Serializable srcValue) {
        this.srcValue = srcValue;
    }

    public Serializable getSrcValue() {
        return srcValue;
    }

    @Override
    public String toString() {
        return String.valueOf(srcValue);
    }
}
