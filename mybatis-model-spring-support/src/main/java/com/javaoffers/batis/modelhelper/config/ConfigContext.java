package com.javaoffers.batis.modelhelper.config;

import com.javaoffers.batis.modelhelper.constants.ConfigPropertiesConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @description:
 * @author: create by cmj on 2023/5/28 14:23
 */
public class ConfigContext {

   public static void init(Environment environment){
       try {
           Field[] declaredFields = ConfigPropertiesConstants.class.getDeclaredFields();
           for(Field field : declaredFields){
               field.setAccessible(true);
               String property = (String)field.get(null);
               String propertyTmp = property.replaceAll(":", ".");
               String value = environment.getProperty(propertyTmp, "");
               if(StringUtils.isNotBlank(value)){
                   System.setProperty(property, value);
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }
   }
}
