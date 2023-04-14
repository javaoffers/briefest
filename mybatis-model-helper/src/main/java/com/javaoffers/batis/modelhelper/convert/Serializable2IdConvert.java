package com.javaoffers.batis.modelhelper.convert;

import com.javaoffers.batis.modelhelper.anno.internal.NotNull;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.batis.modelhelper.core.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * @author mingJie
 */
public class Serializable2IdConvert extends AbstractConver<Serializable, Id> {
    @Override
    public Id convert(Serializable serializable) {
        return newId(serializable);
    }
    public static Id newId(Serializable serializable){
        return new IdImpl(serializable);
    }
}

class IdImpl implements Id {

    private static final ConvertRegisterSelectorDelegate convert = ConvertRegisterSelectorDelegate.convert;

    Serializable srcValue;

    @Override
    public byte toByte() {
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

    public IdImpl(@NotNull Serializable srcValue) {
        this.srcValue = srcValue;
    }

    public Serializable getSrcValue() {
        return srcValue;
    }

    @Override
    public String toString() {
        return String.valueOf(srcValue);
    }

    @Override
    public int hashCode() {
        if(srcValue == null) {
            return 0;
        }
        return srcValue.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(srcValue, obj);
    }
}


