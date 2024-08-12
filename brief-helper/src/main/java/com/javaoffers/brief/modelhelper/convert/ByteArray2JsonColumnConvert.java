package com.javaoffers.brief.modelhelper.convert;

import com.javaoffers.brief.modelhelper.anno.derive.JsonColumn;
import com.javaoffers.brief.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.brief.modelhelper.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;

public class ByteArray2JsonColumnConvert extends AbstractConver<byte[], JsonColumn> {
    @Override
    public JsonColumn convert(byte[] bytes) {
        if(bytes == null){
            return null;
        }
        Class<? extends JsonColumn> desClass = ConvertRegisterSelectorDelegate.processingConvertClass.get();
        return GsonUtils.gson.fromJson(new String(bytes), desClass);
    }
    public ByteArray2JsonColumnConvert(){}
}

class ByteArray2JsonColumnConvert2 extends AbstractConver<Byte[], JsonColumn> {


    @Override
    public JsonColumn convert(Byte[] bytes) {
        if(bytes == null){
            return null;
        }
        byte[] bs = new byte[bytes.length];
        int i = 0;
        for(Byte b : bytes){
            bs[i] = b;
            i++;
        }
        Class<? extends JsonColumn> desClass = ConvertRegisterSelectorDelegate.processingConvertClass.get();
        return GsonUtils.gson.fromJson(new String(bs), desClass);
    }

    public ByteArray2JsonColumnConvert2(){}
}