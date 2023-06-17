package com.javaoffers.brief.modelhelper.proxy;

import com.javaoffers.brief.modelhelper.core.CrudMapperConstant;
import com.javaoffers.brief.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.brief.modelhelper.exception.ParseDataSourceException;
import com.javaoffers.brief.modelhelper.exception.ParseTableException;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.util.HelperUtils;
import com.javaoffers.brief.modelhelper.utils.BriefUtils;
import com.javaoffers.brief.modelhelper.utils.FutureLock;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 解析CrudMapper
 * @Auther: create by cmj on 2022/5/3 00:47
 */
public class CrudMapperProxy<T> implements InvocationHandler, Serializable {

    private MapperProxy<T> mapperProxy;

    private static final Class crudClass = BriefMapper.class;

    private static Map<Method,String> isMapperMethod = BriefUtils.getMapperMethod();

    private Class clazz;

    private volatile BriefMapper briefMapperJql;

    private volatile Type modelClass;

    private volatile JdbcTemplate jdbcTemplate;

    private FutureLock<Pair<Boolean, Exception>> status = new FutureLock();

    private volatile boolean isReady = false;

    public CrudMapperProxy( MapperProxy<T> mapperProxy,Class clazz) {
        this.mapperProxy = mapperProxy;
        this.clazz = clazz;
    }

    private void parseTableInfo(MapperProxy<T> mapperProxy, Class clazz) {
        try {

            Field sqlSessionField = mapperProxy.getClass().getDeclaredField("sqlSession");
            sqlSessionField.setAccessible(true);
            SqlSession sqlSession = (SqlSession)sqlSessionField.get(mapperProxy);
            Field sqlSessionFactoryField = sqlSession.getClass().getDeclaredField("sqlSessionFactory");
            sqlSessionFactoryField.setAccessible(true);
            SqlSessionFactory sessionFactory = (SqlSessionFactory)sqlSessionFactoryField.get(sqlSession);
            DataSource dataSource = sessionFactory.getConfiguration().getEnvironment().getDataSource();
            this.jdbcTemplate = new JdbcTemplate(dataSource);
            if(!crudClass.isAssignableFrom(clazz)){
                return;
            }
            Type[] types = clazz.getGenericInterfaces();
            ParameterizedTypeImpl parameterizedTypes = (ParameterizedTypeImpl)types[0];
            Type modelclass = parameterizedTypes.getActualTypeArguments()[0];
            this.modelClass = modelclass;
            this.briefMapperJql = BriefUtils.newCrudMapper(clazz);
            Connection connection = this.jdbcTemplate.getDataSource().getConnection();
            try {
                List<Class> modelClass = HelperUtils.parseAllModelClass((Class) this.modelClass);
                for(Class mc : modelClass){
                    TableHelper.parseTableInfo(mc, connection);
                }

            }catch (Exception e){
                throw new ParseTableException("parse table info exception", e);
            }finally {
                if(!connection.isClosed()){
                    connection.close();
                }
            }
        }catch (Exception e){
            throw new ParseDataSourceException("parse datasource exception", e);
        }
    }

    private boolean isReady(){
        return this.isReady;
    }

    private void setReadyOk(){
        this.isReady = true;
    }

    /**
     *  Do not debug inside the proxy class May cause unexpected data loss .
     *  Debugging can cause repeated calls from the same thread
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if( !isReady() ){
                // Note that after unLock, tryLock is still unsuccessful. FutureLock can only be used once,
                // unless it can be used again after reset
                if(this.status.tryLock()){
                    try {
                        parseTableInfo(this.mapperProxy, this.clazz);
                        this.status.unlock(Pair.of(true, null));
                        this.setReadyOk();
                    }catch (Exception e){
                        this.status.unlock(Pair.of(false, e));
                        e.printStackTrace();
                        this.status.reset();
                        throw new ParseTableException("parse table info exception", e);
                    }
                }else if(!this.status.getOrOld().getLeft()){
                    throw new ParseTableException("parse table info exception", this.status.get().getRight());
                }
            }
            CrudMapperMethodThreadLocal.addExcutorModel((Class) this.modelClass);
            CrudMapperMethodThreadLocal.addExcutorJdbcTemplate(this.jdbcTemplate);
            //If defaultObj is null, it means BriefMapper interface is not inherited
            if(briefMapperJql != null){
                if(method.getModifiers() == 1){
                    return method.invoke(briefMapperJql,args);
                } else if(StringUtils.isNotBlank(isMapperMethod.get(method)) && args == null){
                    if(method.getName().equals(CrudMapperConstant.SELECT.getMethodName())){
                        return  briefMapperJql.select();
                    }else if(method.getName().equals(CrudMapperConstant.INSERT.getMethodName())){
                        return briefMapperJql.insert();
                    }else if(method.getName().equals(CrudMapperConstant.UPDATE.getMethodName())){
                        return briefMapperJql.update();
                    }else if(method.getName().equals(CrudMapperConstant.DELETE.getMethodName())){
                        return briefMapperJql.delete();
                    }else if(method.getName().equals(CrudMapperConstant.GENERAL.getMethodName())){
                        return briefMapperJql.general();
                    }
                    throw new IllegalAccessException("method not found ");
                }else{
                    //Execute mapperProxy native of batis
                    return mapperProxy.invoke(proxy,method,args);
                }
            }else{
                //Execute mapperProxy native of batis
                return mapperProxy.invoke(proxy,method,args);
            }
        }finally {
            CrudMapperMethodThreadLocal.delExcutorModel();
            CrudMapperMethodThreadLocal.delExcutorJdbcTemplate();
        }

    }
}
