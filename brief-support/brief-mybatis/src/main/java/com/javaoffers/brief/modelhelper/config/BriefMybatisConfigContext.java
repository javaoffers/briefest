package com.javaoffers.brief.modelhelper.config;

import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;

/**
 * @description:
 * @author: create by cmj on 2023/5/28 14:23
 */
public class BriefMybatisConfigContext {

   public static void init(Environment environment){
       try {
           Field[] declaredFields = ConfigPropertiesConstants.class.getDeclaredFields();
           for(Field field : declaredFields){
               field.setAccessible(true);
               String property = (String)field.get(null);
               String propertyTmp = property.replaceAll(":", ".");
               String value = environment.getProperty(propertyTmp, "");
               if(StringUtils.isNotBlank(value)){
                   BriefProperties.put(property, value);
               }
           }
           BriefProperties.freshAll();
       }catch (Exception e){
           e.printStackTrace();
       }
   }
}
