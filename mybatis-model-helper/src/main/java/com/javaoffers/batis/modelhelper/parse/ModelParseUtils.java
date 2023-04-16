package com.javaoffers.batis.modelhelper.parse;

import com.javaoffers.batis.modelhelper.utils.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/7 20:01
 */
public class ModelParseUtils {

    static final int batch = 100;

    static final ModelParse modelParse = new SmartModelParse();

    public static <E> List<E> converterMap2Model(Class<E> clazz, List<Map<String, Object>> listMap) {
        if (listMap.size() > batch){
            List<Lists.ListData<Map<String, Object>>> partition = Lists.partition(listMap, batch);
            List<E>[] slot = new List[partition.size()];
            ArrayList<E> resutl = new ArrayList<>();
            partition.parallelStream().forEach(list->{
                slot[list.getPartitionIndex()] = modelParse.converterMap2Model(clazz, list.getList()) ;
            });
            Arrays.stream(slot).forEach(resutl::addAll);
            return resutl;
        }else{
            return modelParse.converterMap2Model(clazz, listMap);
        }

    }
}
