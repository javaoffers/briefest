package com.javaoffers.base.batis.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/7 19:57
 */
public interface ModelParse {

    /**
     * 模型解析
     * @param clazz
     * @param listMap
     * @param <E>
     * @return
     */
    public <E> ArrayList<E> converterMap2Model(Class<E> clazz, List<Map<String, Object>> listMap);

}
