package com.javaoffers.brief.modelhelper.parse;

import com.javaoffers.brief.modelhelper.jdbc.ResultSetExecutor;
import com.javaoffers.brief.modelhelper.utils.Lists;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/7 20:01
 */
public class ModelParseUtils {

    static final int batch = 100;

    static final ModelParse modelParse = new SmartModelParse();

    static final RealtimeModelParse realtimeSmartModelParse = new RealtimeSmartModelParse();

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

    public static <E> List<E>  converterResultSet2ModelForJoinSelect(Class<E> clazz, ResultSetExecutor rs) {
        return realtimeSmartModelParse.converterResultSet2ModelForJoinSelect(clazz, rs);
    }

    public static <E> List<E> converterResultSet2ModelForNormalSelect(Class<E> modelClass, ResultSetExecutor briefResultSetExecutor) {
        return realtimeSmartModelParse.converterResultSet2ModelForNormalSelect(modelClass, briefResultSetExecutor);
    }

    public static <E> void  converterResultSet2ModelForJoinSelectStream(Class<E> clazz, ResultSetExecutor rs, Consumer<E> consumer) {
         realtimeSmartModelParse.converterResultSet2ModelForJoinSelectStream(clazz, rs, consumer);
    }

    public static <E> void converterResultSet2ModelForNormalSelectStream(Class<E> modelClass, ResultSetExecutor briefResultSetExecutor, Consumer<E> consumer) {
         realtimeSmartModelParse.converterResultSet2ModelForNormalSelectStream(modelClass, briefResultSetExecutor,consumer);
    }
}
