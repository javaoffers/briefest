package com.javaoffers.batis.modelhelper.aggent;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import org.apache.commons.lang3.tuple.Pair;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @description: 额外处理器
 * @author: caomingjiecode@outlook.com
 * @create: 2021-12-06 下午5:35
 */
public class MapperProxyAggentProcess {

    private static final InheritableThreadLocal<Boolean> ISMODEL = new InheritableThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    public static void markModel(){
        ISMODEL.set(true);
    }
    public static boolean isModel(){
        return ISMODEL.get();
    }
    public static void resetModel(){
        ISMODEL.set(false);
    }
    /**
     * 检查是否属于Model
     * @param method
     * @return
     */
    public static Pair<Boolean,Class> checkIsModel(Method method){
        Type genericReturnType = method.getGenericReturnType();
        Pair<Boolean, Class> md = checkIsModel(genericReturnType);
        if (md != null) return md;
        return Pair.of(false,null);
    }

    private static Pair<Boolean, Class> checkIsModel(Type genericReturnType) {
        if(genericReturnType instanceof ParameterizedTypeImpl){
            ParameterizedTypeImpl ptz = (ParameterizedTypeImpl) genericReturnType;
            Type[] actualTypeArguments = ptz.getActualTypeArguments();
            if(actualTypeArguments!=null&&actualTypeArguments.length>0){
                Type actualTypeArgument = actualTypeArguments[0];
                if( !(actualTypeArgument instanceof Class)){ return Pair.of(false,null); }
                Class md = (Class) actualTypeArgument;
                Annotation mdDeclaredAnnotation = md.getDeclaredAnnotation(BaseModel.class);
                if(mdDeclaredAnnotation!=null){
                    return Pair.of(true,md);
                }
            }
        }else if(genericReturnType instanceof Class){
            Annotation annotation = ((Class) genericReturnType).getDeclaredAnnotation(BaseModel.class);
            if(annotation!=null){
                return  Pair.of(true,(Class) genericReturnType);
            }
        }
        return null;
    }

    /**
     * 获取方法的返回值
     * @param method
     * @return
     */
    public static Class getRetrunClass(Method method){
        Class<?> returnType = method.getReturnType();
        return returnType;
    }

    public static Pair<Boolean,Collection> isCollection(Method method){

        Class retrunClass = getRetrunClass(method);
        boolean status = Collection.class.isAssignableFrom(retrunClass) || retrunClass ==Collection.class;
        if(status ){
            if(!Modifier.isAbstract(retrunClass.getModifiers())){
                try {
                    Constructor declaredConstructor = retrunClass.getDeclaredConstructor();
                    declaredConstructor.setAccessible(true);
                    Collection o = (Collection) declaredConstructor.newInstance();
                    return Pair.of(status,o);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                if(List.class.isAssignableFrom(retrunClass)){
                    return Pair.of(status,new LinkedList());
                }
                if(Set.class.isAssignableFrom(retrunClass)){
                    return Pair.of(status,new HashSet());
                }
            }
        }
        return Pair.of(false,null);
    }

}
