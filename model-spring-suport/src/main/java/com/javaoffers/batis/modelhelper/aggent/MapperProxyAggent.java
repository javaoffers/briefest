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
        Pair<Boolean, Class> isModel = MapperProxyAggentProcess.checkIsModel(method);
        Object execute = null;
        if(isModel.getLeft()){
            try {
                MapperProxyAggentProcess.markModel();
                execute = mapperMethod.execute(sqlSession, args);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                MapperProxyAggentProcess.resetModel();
            }
            if(execute instanceof Collection || execute instanceof Map){//resultType : model
                Collection result = Collections.EMPTY_LIST;
                if(execute instanceof Collection){
                    result  = (Collection)execute;
                }else {
                    result = new LinkedList<>();
                    result.add(execute);
                }
                ArrayList models = ModelParseUtils.converterMap2Model(isModel.getRight(), new LinkedList<>(result));
                Pair<Boolean, Collection> collection = MapperProxyAggentProcess.isCollection(method);
                if(collection.getLeft()){
                    Collection right = collection.getRight();right.addAll(models);
                    return right;
                }
                if(models.size()>0){
                    return models.get(0);
                }
                Constructor constructor = MapperProxyAggentProcess.getRetrunClass(method).getConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            }
        }else {
            execute = mapperMethod.execute(sqlSession, args);
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
