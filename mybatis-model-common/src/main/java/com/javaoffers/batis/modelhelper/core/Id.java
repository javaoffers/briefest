package com.javaoffers.batis.modelhelper.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 插入返回的id抽象接口
 * @author create by cmj
 */
public interface Id extends Serializable{

    Id EMPTY_ID = new Id() {
        @Override
        public int toByte() {
            return Integer.MIN_VALUE;
        }

        @Override
        public int toInt() {
            return Integer.MIN_VALUE;
        }

        @Override
        public long toLong() {
            return Integer.MIN_VALUE;
        }

        @Override
        public BigDecimal toBd() {
            return new BigDecimal(Integer.MIN_VALUE);
        }

        @Override
        public BigInteger toBi() {
            return new BigInteger(String.valueOf(Integer.MIN_VALUE));
        }

        @Override
        public Number toNumber() {
            return new Integer(Integer.MIN_VALUE);
        }

        @Override
        public Serializable value() {
            return Integer.MIN_VALUE;
        }
    };

    int toByte();

    int toInt();

    long toLong();

    BigDecimal toBd();

    BigInteger toBi();

    Number toNumber();

    String toString();

    Serializable value();


}
