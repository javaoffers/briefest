package com.javaoffers.brief.modelhelper.encrypt;

import com.javaoffers.brief.modelhelper.briefstate.BriefCommonComponentStates;
import com.javaoffers.brief.modelhelper.encrypt.batis.MybatisEncryptInterceptorConfig;
import com.javaoffers.brief.modelhelper.exception.SqlAesProcessException;
import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author mingJie
 */
//@Slf4j
@Order
public class MybatisAesEncryptConfigBeanRegistrar implements ImportBeanDefinitionRegistrar , BeanFactoryPostProcessor {

    private static BeanDefinitionRegistry registry;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        MybatisAesEncryptConfigBeanRegistrar.registry = registry;
        //注册 MybatisAesEncryptConfigBeanRegistrar
        BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MybatisAesEncryptConfigBeanRegistrar.class);
        registry.registerBeanDefinition(MybatisAesEncryptConfigBeanRegistrar.class.getName(), beanDefinition.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        if(BriefCommonComponentStates.ENCRYPT_STATE){
            //注册mybatis拦截器AES加密
            if(!registry.containsBeanDefinition(MybatisEncryptInterceptorConfig.class.getName())){
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MybatisEncryptInterceptorConfig.class);
                registry.registerBeanDefinition(MybatisEncryptInterceptorConfig.class.getName(), beanDefinitionBuilder.getBeanDefinition());
            }

            String[] beanNamesForType = configurableListableBeanFactory.getBeanNamesForType(SqlAesProcessor.class);
            ArrayList<SqlAesProcessor> sqlAesProcessors = Lists.newArrayList();
            if(beanNamesForType != null){
                for(String beanName : beanNamesForType){
                    sqlAesProcessors.add((SqlAesProcessor)configurableListableBeanFactory.getBean(beanName));
                }
            }
            try {
                String jqlAesInterceptorClassName = "com.javaoffers.brief.modelhelper.encrypt.JqlAesInterceptor";
                Class<?> jqlAesInterceptorClass = Class.forName(jqlAesInterceptorClassName);
                Method[] declaredMethods = jqlAesInterceptorClass.getDeclaredMethods();
                Method setSqlAesProcessors = null;
                for(Method method : declaredMethods){
                    boolean ok = method.getName().equalsIgnoreCase("setSqlAesProcessors");
                    if(ok){
                        method.setAccessible(true);
                        setSqlAesProcessors = method;
                        break;
                    }
                }
                JqlInterceptor jqlAesInterceptor = (JqlInterceptor)configurableListableBeanFactory.getBean(jqlAesInterceptorClass);
                setSqlAesProcessors.invoke(jqlAesInterceptor, sqlAesProcessors);
            }catch (Exception e){
                if(e instanceof ClassNotFoundException){
                    //ignore
                }else{
                    e.printStackTrace();
                    throw new SqlAesProcessException(e.getMessage());
                }

            }
        }
    }
}
