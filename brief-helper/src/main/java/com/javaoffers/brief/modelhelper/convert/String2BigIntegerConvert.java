package com.javaoffers.brief.modelhelper.convert;

import java.math.BigInteger;

/**
 * @Description: String2BigIntegerConvert
 * @Auther: create by cmj on 2021/12/10 13:00
 */
public class String2BigIntegerConvert extends AbstractConver<String, BigInteger> {
    @Override
    public BigInteger convert(String s) {
        return new BigInteger(s);
    }
}
