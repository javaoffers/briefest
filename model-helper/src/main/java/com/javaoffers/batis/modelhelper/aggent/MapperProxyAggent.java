package com.javaoffers.batis.modelhelper.aggent;

import com.javaoffers.batis.modelhelper.parse.ModelParseUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSession;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @description: MapperProxyAggent
 * @author: caomingjiecode@outlook.com
 * @create: 2021-12-06 下午4:10
 */
public class MapperProxyAggent<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -6424540398559729838L;
    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxyAggent(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                throw ExceptionUtil.unwrapThrowable(t);
            }
        }
        final MapperMethod mapperMethod = cachedMapperMethod(method);
        Object execute = mapperMethod.execute(sqlSession, args);
        if(execute instanceof Collection){//resultType : model
            Collection result = (Collection)execute;
            Pair<Boolean, Class> isModel = MapperProxyAggentProcess.checkIsModel(method);
            if(isModel.getLeft()){
                ArrayList models = ModelParseUtils.converterMap2Model(isModel.getRight(), new LinkedList<>(result));
                Pair<Boolean, Collection> collection = MapperProxyAggentProcess.isCollection(method);
                if(collection.getLeft()){
                   return collection.getRight().addAll(models);
                }
                if(models.size()>0){
                    return models.get(0);
                }
                Constructor constructor = MapperProxyAggentProcess.getRetrunClass(method).getConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            }
        }
        return execute;
    }

    private MapperMethod cachedMapperMethod(Method method) {
        MapperMethod mapperMethod = methodCache.get(method);
        if (mapperMethod == null) {
            mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
            methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }
}