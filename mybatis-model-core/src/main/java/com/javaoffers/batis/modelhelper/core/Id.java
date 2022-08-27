package com.javaoffers.batis.modelhelper.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 插入返回的id抽象接口
 * @author create by cmj
 */
public interface Id extends Serializable{

    int toByte();

    int toInt();

    long toLong();

    BigDecimal toBd();

    BigInteger toBi();

    Number toNumber();

    String toString();

    Serializable value();


}
