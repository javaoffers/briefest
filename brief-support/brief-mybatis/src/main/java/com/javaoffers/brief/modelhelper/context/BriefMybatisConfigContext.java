package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.constants.ConfigPropertiesConstants;
import com.javaoffers.brief.modelhelper.jdbc.spring.SpringBriefContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;

/**
 * @description:
 * @author: create by cmj on 2023/5/28 14:23
 */
public class BriefMybatisConfigContext {

   public static void init(ConfigurableListableBeanFactory beanFactory){
       try {
           SmartBriefContext smartBriefContext = new SpringMybatisBriefContext(beanFactory);
           smartBriefContext.fresh();
       }catch (Exception e){
           e.printStackTrace();
       }
   }
}
