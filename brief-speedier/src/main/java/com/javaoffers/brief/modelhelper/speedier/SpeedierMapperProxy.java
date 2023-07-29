package com.javaoffers.brief.modelhelper.speedier;

import com.javaoffers.brief.modelhelper.core.CrudMapperConstant;
import com.javaoffers.brief.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.brief.modelhelper.exception.ParseTableException;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;
import com.javaoffers.brief.modelhelper.util.HelperUtils;
import com.javaoffers.brief.modelhelper.utils.BriefUtils;
import com.javaoffers.brief.modelhelper.utils.FutureLock;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class SpeedierMapperProxy  implements InvocationHandler, Serializable {

    private static final Map<Method, String> mapperMethod = BriefUtils.getMapperMethod();

    private static final Map<Method, String> objectMethod = BriefUtils.getObjectMethod();

    private BriefMapper briefMapperJql;

    private volatile Class modelClass;

    private volatile DataSource dataSource;

    private FutureLock<Pair<Boolean, Exception>> status = new FutureLock();

    private volatile boolean isReady = false;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        try {
            if( !isReady() ){
                // Note that after unLock, tryLock is still unsuccessful. FutureLock can only be used once,
                // unless it can be used again after reset
                if(this.status.tryLock()){
                    Connection connection = this.dataSource.getConnection();
                    try {
                        List<Class> modelClass = HelperUtils.parseAllModelClass((Class) this.modelClass);
                        for(Class mc : modelClass){
                            TableHelper.parseTableInfo(mc, connection);
                        }
                        this.status.unlock(Pair.of(true, null));
                        this.setReadyOk();
                    }catch (Exception e){
                        this.status.unlock(Pair.of(false, e));
                        e.printStackTrace();
                        this.status.reset();
                        throw new ParseTableException("parse table info exception", e);
                    }finally {
                        if(!connection.isClosed()){
                            connection.close();
                        }
                    }
                }else if(!this.status.getOrOld().getLeft()){
                    throw new ParseTableException("parse table info exception", this.status.get().getRight());
                }
            }
            CrudMapperMethodThreadLocal.addExcutorModel( this.modelClass);
            CrudMapperMethodThreadLocal.addExcutorDataSource(this.dataSource);

            if(method.getModifiers() == 1){
                return method.invoke(briefMapperJql,args);
            } else if(StringUtils.isNotBlank(mapperMethod.get(method)) && args == null){
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
            }else if(StringUtils.isNotBlank(objectMethod.get(method))){
                return method.invoke(briefMapperJql, args);
            }
            throw new IllegalAccessException("method not support ");
        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }finally {
            CrudMapperMethodThreadLocal.delExcutorModel();
            CrudMapperMethodThreadLocal.delExcutorDataSource();
        }
    }

    SpeedierMapperProxy(BriefMapper briefMapperJql, DataSource dataSource, Class modelClass) {
        this.briefMapperJql = briefMapperJql;
        this.dataSource = dataSource;
        this.modelClass = modelClass;
    }

    private boolean isReady(){
        return this.isReady;
    }

    private void setReadyOk(){
        this.isReady = true;
    }
}
