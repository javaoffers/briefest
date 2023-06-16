package com.javaoffers.brief.modelhelper.convert;

public class ByteArray2StringConvert extends AbstractConver<byte[], String> {
    @Override
    public String convert(byte[] bytes) {
        return new String(bytes);
    }
}

class ByteArray2StringConvert2 extends AbstractConver<Byte[], String> {
    @Override
    public String convert(Byte[] bytes) {
        byte[] bs = new byte[bytes.length];
        if(bytes.length>0){
            int i = 0;
            for(Byte b : bytes){
                bs[i] = b;
                i++;
            }
        }
        return new String(bs);
    }

    public ByteArray2StringConvert2() {
    }
}
