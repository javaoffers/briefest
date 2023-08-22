package com.javaoffers.brief.modelhelper.convert;

import com.google.gson.Gson;
import com.javaoffers.brief.modelhelper.anno.derive.JsonColumn;
import com.javaoffers.brief.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.brief.modelhelper.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class String2JsonColumnConvert extends AbstractConver<String, JsonColumn> {
    @Override
    public JsonColumn convert(String json) {
        if(StringUtils.isBlank(json)){
            return null;
        }
        Class<? extends JsonColumn> desClass = ConvertRegisterSelectorDelegate.processingConvertClass.get();
        return GsonUtils.gson.fromJson(json, desClass);
    }
}
