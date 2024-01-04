package com.javaoffers.brief.modelhelper.filter;

import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveFlag;
import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveInfo;
import com.javaoffers.brief.modelhelper.log.JqlLogger;
import com.javaoffers.brief.modelhelper.utils.Assert;
import com.javaoffers.brief.modelhelper.utils.CglibProxyUtils;
import com.javaoffers.brief.modelhelper.utils.ColNameAndColValueUtils;
import com.javaoffers.brief.modelhelper.utils.ColumnInfo;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: SmartJqlChainFilter
 * <p>
 * Record the slow SQL, Secure Dynamic Updates when call setter methods of model.
 * </p>
 * @author: create by cmj on 2023/6/1 20:31
 */
public class SmartJqlChainFilter implements JqlChainFilter {
    @Override
    public Object filter(Chain<Object, SqlMetaInfo> chain) {
        long cost = 0;
        SqlMetaInfo metaInfo = chain.getMetaInfo();
        Class modelClass = metaInfo.getModelClass();
        TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
        DeriveInfo autoUpdateDerive = tableInfo.getDeriveColName(DeriveFlag.AUTO_UPDATE);
        long startTime = System.currentTimeMillis();
        Object o = chain.doChain();
        long endTime = System.currentTimeMillis();
        if ((cost = endTime - startTime) > JqlLogger.time) {
            if(o instanceof List){
                JqlLogger.infoSqlCost("COST TIME : {}, SIZE: {}", cost, ((List) o).size());
            }else{
                JqlLogger.infoSqlCost("COST TIME : {}", cost);
            }
        }
        if (autoUpdateDerive != null) {
            o = processAutoUpdate(metaInfo, modelClass, tableInfo, o);
        }

        return o;
    }

    //auto update col name
    private Object processAutoUpdate(SqlMetaInfo metaInfo, Class modelClass, TableInfo tableInfo, Object o) {

        //获取主键的值
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        Assert.isTrue(MapUtils.isNotEmpty(primaryColNames), modelClass.getName()
                + " @BaseUnique not found, Please use the @BaseUnique designated the only fields/colName");

        if (modelClass.isAssignableFrom(o.getClass())) {
            o = doProcessAutoUpdate(metaInfo, modelClass, tableInfo, o);
        } else if(o instanceof Collection && CollectionUtils.isNotEmpty((Collection<?>) o)){
            Collection oList = (Collection)o;
            Object next = oList.iterator().next();
            if(modelClass.isAssignableFrom(next.getClass())){
                ArrayList<Object> objects = new ArrayList<>(oList.size());
                for(Object o1 : oList){
                    o1 = doProcessAutoUpdate(metaInfo, modelClass, tableInfo, o1);
                    objects.add(o1);
                }
                oList.clear();
                return objects;
            }
        }
        return o;
    }

    private Object doProcessAutoUpdate(SqlMetaInfo metaInfo, Class modelClass, TableInfo tableInfo, Object o) {
        //获取主键的值
        final Map<String, Object> uniqueColValue = new ConcurrentHashMap<>(ColNameAndColValueUtils.parseUniqueCoNameAndUniqueColValue(o, modelClass));
        if (!MapUtils.isEmpty(uniqueColValue)) {
            o = CglibProxyUtils.createProxy(o, (Object proxyObject, Object obj, Method method, Object[] args, MethodProxy proxy) -> {

                if (args != null && args.length == 1) {
                    Object value = args[0];
                    // name of method
                    String name = method.getName();
                    boolean isSetMethod = false;
                    if (name.startsWith("set")) {
                        isSetMethod = true;
                        name = name.substring(3, name.length());
                    } else if (name.startsWith("is")) {
                        isSetMethod = true;
                        name = name.substring(2, name.length());
                    }

                    //get fieldName
                    if (StringUtils.isNotBlank(name)) {
                        if (name.length() == 1) {
                            name = name.substring(0, 1).toLowerCase();
                        } else {
                            name = name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
                        }
                    }

                    //If there is a difference
                    if(!Objects.deepEquals(tableInfo.getFieldNameAndField().get(name).get(obj), value)){

                        // fieldName mapping colName
                        Map<String, String> fieldNameColName = tableInfo.getFieldNameColNameOfModel();

                        //Cancel the differences to update
                        if(uniqueColValue.containsKey(fieldNameColName.get(name)) &&  value == null){
                            uniqueColValue.clear();
                        }

                        if (isSetMethod) {
                            String updateTag = "A_" + name + "_U";
                            StringBuilder sql = new StringBuilder("update ")
                                    .append(tableInfo.getTableName())
                                    .append(" set ")
                                    .append(fieldNameColName.get(name))
                                    .append(" = #{" + updateTag + "} where 1=1");

                            uniqueColValue.forEach((ucolName, ucolValue) -> {
                                sql.append(" and ");
                                sql.append(ucolName);
                                sql.append(" = ");
                                sql.append("#{").append(ucolName).append("}");
                            });
                            //will update col name
                            HashMap<String, Object> params = new HashMap<>(uniqueColValue);
                            //Avoid multithreading issues
                            if(params.size() != 0){
                                params.put(updateTag, args[0]);
                                String sqlStr = sql.toString();
                                JqlLogger.infoSql("SQL: {}", sqlStr);
                                JqlLogger.infoSql("PAM: {}", params);
                                metaInfo.getBaseBrief().updateData(sqlStr, params);
                            }
                        }
                    }
                }
                return method.invoke(obj, args);
            });
        }
        return o;
    }

}
