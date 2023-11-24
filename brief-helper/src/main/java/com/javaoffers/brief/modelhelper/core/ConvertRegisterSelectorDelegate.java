package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.anno.internal.NotNull;
import com.javaoffers.brief.modelhelper.convert.AbstractConver;
import com.javaoffers.brief.modelhelper.convert.Convert;
import com.javaoffers.brief.modelhelper.utils.ReflectionUtils;
import com.javaoffers.brief.modelhelper.utils.BlurUtils;
import com.javaoffers.brief.modelhelper.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

/**
 * @Description: Selector represents and selector uses entry
 * @Auther: create by cmj on 2021/12/9 10:52
 */
public class ConvertRegisterSelectorDelegate {

    private ModelApplicationContext applicationContext = new ModelApplicationContext();

    public final static ConvertRegisterSelectorDelegate convert = new ConvertRegisterSelectorDelegate();

    public final static ThreadLocal<Class> processingConvertClass = new ThreadLocal<>();

    static Convert  convertSame = new SameConvert();

    static class SameConvert implements Convert<Object, Object>{
        @Override
        public Object convert(Object s) {
            return s;
        }
    }

    private ConvertRegisterSelectorDelegate() {
    }

    {
        Set<Class<? extends Convert>> converts = ReflectionUtils.getChilds(Convert.class);
        for (Class c : converts) {
            if (Modifier.isAbstract(c.getModifiers())) {
                continue;
            }
            try {
                if(c.isAssignableFrom(SameConvert.class)){
                    continue;
                }
                Constructor constructor = c.getConstructor();
                constructor.setAccessible(true);
                registerConvert((AbstractConver) constructor.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * registerConvert
     *
     * @param convert
     */
    private void registerConvert(AbstractConver convert) {
        convert.register(applicationContext);
    }

    /**
     * selector
     *
     * @return
     */
    private Convert selector(Class src, Class des) {
        Convert convert = applicationContext.selector(new ConverDescriptor(src, des));
        if (convert == null) {
            Set<Class> srcSupers = Utils.getSupperClass(src);

            //src 升级
            for (Class srcc : srcSupers) {
                convert = selectorOnly(srcc, des);
                if (convert != null) {
                    break;
                }
            }
            //des 降级
            if (convert == null) {
                Set<Class<?>> desSupers = ReflectionUtils.getChilds(des);
                for (Class dess : desSupers) {
                    convert = selectorOnly(src, dess);
                    if (convert != null) {
                        break;
                    }
                }
            }

            //des特殊升级
            if(convert == null){
                Set<Class> desSupers = Utils.getSupperClass(des);
                for(Class desSupper : desSupers){
                    convert = selectorOnly(src, desSupper);
                    if(convert != null){
                        break;
                    }
                }
            }

            //Avoid Upgrading or Downgrading Query Transformers Next Time
            if (convert != null) {
                applicationContext.registerConvert(new ConverDescriptor(src, des), convert);
            }
        }
        return convert;
    }

    private Convert selectorOnly(Class src, Class des) {
        return applicationContext.selector(new ConverDescriptor(src, des));
    }

    private LinkedList<Class> getSupers(Class c) {
        Class superclass = c.getSuperclass();
        Class[] interfaces = c.getInterfaces();
        LinkedList<Class> srcs = new LinkedList<>();
        if (superclass != Object.class && superclass != null) {
            Collections.addAll(srcs, superclass);
        }
        for (Class srcInter : interfaces) {
            if (srcInter != null) {
                Collections.addAll(srcs, srcInter);
            }
        }
        return srcs;

    }

    /**
     * 类型转换,支持模糊渲染
     *
     * @param des      要转换的目标类型
     * @param srcValue 原始值类型
     * @param <T>
     * @return
     */
    public <T> T converterObject(Class<T> des, Object srcValue, Field field) {
        try {
            T t = (converterObject(des, srcValue));
            Annotation anno = null;
            if (t instanceof String && StringUtils.isNotBlank((String) t)
                    && (anno = Utils.getBlurAnnotation(field)) != null) {
                t = (T) BlurUtils.processDeriveAnno(anno, (String) t);
            }
            return t;
        } catch (ClassCastException e) {

            throw new ClassCastException(e.getMessage() + " the field name is " + field.getName());
        }
    }

    /**
     * 类型转换
     *
     * @param des      要转换的目标类型
     * @param srcValue 原始值类型
     * @param <T>
     * @return
     */
    public <T> T converterObject(Class<T> des, @NotNull Object srcValue) {
        Class<T> orgDes = des;
        //基础类型转换为包装类型，如果存在基础类型
        des = Utils.baseClassUpgrade(des);
        Class src = Utils.baseClassUpgrade(srcValue.getClass());

        if (des == src || des.isAssignableFrom(src)) {
            return (T) srcValue;
        }
        //选取 convert
        Convert convert = selector(srcValue.getClass(), des);
        //开始转换
        if (convert == null) {
            throw new ClassCastException("Origin type:" + srcValue.getClass().getName() + " dont convert to  Target type: " + des.getName());
        }
        T desObject = null;
        try {
            processingConvertClass.set(orgDes);
            desObject = (T) convert.convert(src.cast(srcValue));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            processingConvertClass.remove();
        }
        return desObject;
    }

    /**
     * 获取一个转换器代理.
     *
     * @param des
     * @param srcValue
     * @param field
     * @param <S>
     * @param <T>
     * @return
     */
    public <S, T> ConvertDelegate<T> choseConverter(Class<T> des, S srcValue, Field field) {
        //源类型
        Class<T> orgDes = des;
        //基础类型转换为包装类型，如果存在基础类型
        des = Utils.baseClassUpgrade(des);
        Convert convert = null;
        Class srcUpgrade = Utils.baseClassUpgrade(srcValue.getClass());

        if (des == srcUpgrade || des.isAssignableFrom(srcUpgrade)) {
            convert = convertSame;
        } else {
            //选取 convert
            convert = selector(srcValue.getClass(), des);
            //开始转换
            if (convert == null) {
                throw new ClassCastException("Origin type:" + srcValue.getClass().getName() + " dont convert to  Target type: " + des.getName());
            }
        }

        ConvertDelegate<T> convertDelegate = new ConvertDelegate<>(srcUpgrade, orgDes, convert);
        Annotation anno = null;
        if ((anno = Utils.getBlurAnnotation(field)) != null) {
            Annotation finalAnno = anno;
            convertDelegate.setAfterProcess((value) -> {
                if (value instanceof String && StringUtils.isNotBlank((String) value)) {
                    value = (T) BlurUtils.processDeriveAnno(finalAnno, (String) value);
                }
                return value;
            });
        }
        return convertDelegate;
    }

    /**
     * 获取一个转换器代理. 没有后置处理器
     *
     * @param des
     * @param srcValue
     * @param <S>
     * @param <T>
     * @return
     */
    public <S, T> ConvertDelegate<T> choseConverter(Class<T> des, S srcValue) {
        //源类型
        Class<T> orgDes = des;
        //基础类型转换为包装类型，如果存在基础类型
        des = Utils.baseClassUpgrade(des);
        Convert convert = null;
        Class srcUpgrade = Utils.baseClassUpgrade(srcValue.getClass());

        if (des == srcUpgrade || des.isAssignableFrom(srcUpgrade)) {
            convert = convertSame;
        } else {
            //选取 convert
            convert = selector(srcValue.getClass(), des);
            //开始转换
            if (convert == null) {
                throw new ClassCastException("Origin type:" + srcValue.getClass().getName() + " dont convert to  Target type: " + des.getName());
            }
        }

        ConvertDelegate<T> convertDelegate = new ConvertDelegate<>(srcUpgrade, orgDes, convert);
        return convertDelegate;
    }

    public static Class getProcessingConvertDesClass() {
        return processingConvertClass.get();
    }


}
