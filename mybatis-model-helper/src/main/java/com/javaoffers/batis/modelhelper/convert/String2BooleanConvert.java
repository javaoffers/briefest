package com.javaoffers.batis.modelhelper.convert;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: String2BooleanConvert
 * @Auther: create by cmj on 2021/12/10 12:17
 */
public class String2BooleanConvert extends AbstractConver<String,Boolean> {
    @Override
    public Boolean convert(String bool) {
        return StringUtils.isBlank(bool) ? false : bool.trim().equalsIgnoreCase("true");
    }
}
