package com.javaoffers.brief.modelhelper.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 插入返回的id抽象接口
 * @author create by cmj
 */
public interface Id extends Serializable{
    byte EMPTY_ID_VALUE = -1;
    Id EMPTY_ID = new Id() {
        @Override
        public byte toByte() {
            return EMPTY_ID_VALUE;
        }

        @Override
        public int toInt() {
            return EMPTY_ID_VALUE;
        }

        @Override
        public long toLong() {
            return EMPTY_ID_VALUE;
        }

        @Override
        public BigDecimal toBd() {
            return new BigDecimal(EMPTY_ID_VALUE);
        }

        @Override
        public BigInteger toBi() {
            return new BigInteger(String.valueOf(EMPTY_ID_VALUE));
        }

        @Override
        public Number toNumber() {
            return new Integer(EMPTY_ID_VALUE);
        }

        @Override
        public Serializable value() {
            return EMPTY_ID_VALUE;
        }
    };

    byte toByte();

    int toInt();

    long toLong();

    BigDecimal toBd();

    BigInteger toBi();

    Number toNumber();

    String toString();

    Serializable value();


}
