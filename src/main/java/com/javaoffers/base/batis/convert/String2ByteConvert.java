package com.javaoffers.base.batis.convert;

/**
 * @Description: String2ByteConvert
 * @Auther: create by cmj on 2021/12/10 12:17
 */
public class String2ByteConvert extends AbstractConver<String,Byte> {
    @Override
    public Byte convert(String s) {
        byte b = Byte.parseByte(s);
        return b;
    }
}
