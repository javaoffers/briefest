package com.javaoffers.brief.modelhelper.filter.impl;

import com.javaoffers.brief.modelhelper.filter.JqlFunFilter;
import com.javaoffers.brief.modelhelper.utils.BlurUtils;

import java.lang.reflect.Field;

/**
 * @description: The field as SQL fun. But not a must be SQL fun. We just use SQL fun features.
 * Avoid will refresh the database data update or save
 * @author: create by cmj on 2023/3/25 20:39
 */
public class AsSqlFunFilterImpl implements JqlFunFilter {

    @Override
    public Boolean filter(Field field) {
        if(field != null){
           return BlurUtils.containsBlurAnno(field);
        }
        return false;
    }
}
