package com.javaoffers.base.batis.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/7 20:01
 */
public class ModelParseUtils {

    static  final ModelParse modelParse = new SmartModelParse();

    public static <E> ArrayList<E> converterMap2Model(Class<E> clazz, List<Map<String, Object>> listMap) {
        return modelParse.converterMap2Model(clazz,listMap);
    }
}
