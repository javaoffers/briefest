package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.convert.Convert;
import com.javaoffers.brief.modelhelper.utils.BlurUtils;
import com.javaoffers.brief.modelhelper.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author mingJie
 */
public class ConvertDelegate<T> implements ConvertProxy<T>{

    static ConvertRegisterSelectorDelegate choseConverter = ConvertRegisterSelectorDelegate.convert;

    private AtomicInteger defaultConvertSuccessCount = new AtomicInteger();

    /**
     * 如果是基础类会被升级
     */
    private Class srcUpgrade;

    /**
     * 源目标类,没有被升级.
     */
    private Class orgDes;

    /**
     * 转换器
     */
    private Convert convert;

    /**
     * 后置处理字段属性,用户处理字段的附属信息,比如脱贫,该子段可能不存在.
     */
    private Function<T, T> afterProcess;

    public T convert(Object srcValue){
        T desObject = null;
        try {
            ConvertRegisterSelectorDelegate.processingConvertClass.set(orgDes);
            desObject = (T) convert.convert(srcUpgrade.cast(srcValue));
            defaultConvertSuccessCount.incrementAndGet();
        }catch (Exception e){
            defaultConvertSuccessCount.decrementAndGet();
            //重新选择转换
            ConvertDelegate<?> convertDelegate = choseConverter.choseConverter(orgDes, srcValue);
            //选择是否替换convert. 如果失败次数小于５则进行替换
            if(defaultConvertSuccessCount.get() < -5){
                this.convert = convertDelegate.convert;
                this.srcUpgrade = convertDelegate.srcUpgrade;
                defaultConvertSuccessCount.set(0);
            }
            desObject = (T) convertDelegate.convert(srcValue);
        }finally {
            ConvertRegisterSelectorDelegate.processingConvertClass.remove();
        }
        if(afterProcess != null){
           desObject =  afterProcess.apply(desObject);
        }
        return desObject;
    }

    public ConvertDelegate(Class srcUpgrade, Class orgDes, Convert convert) {
        this.srcUpgrade = srcUpgrade;
        this.orgDes = orgDes;
        this.convert = convert;
    }

    public void setAfterProcess(Function<T, T> afterProcess) {
        this.afterProcess = afterProcess;
    }
}
