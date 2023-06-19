package com.javaoffers.brief.modelhelper.encrypt;

import com.javaoffers.brief.modelhelper.encrypt.anno.AesEncryptConfig;
import com.javaoffers.brief.modelhelper.encrypt.batis.MybatisEncryptInterceptorConfig;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.util.ArrayList;

/**
 * @author mingJie
 */
//@Slf4j
@ConditionalOnBean(AesEncryptConfigBeanRegistrar.class)
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
        if(AesEncryptConfigBeanRegistrar.isOpenAseEncrpt){
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
            JqlAesInterceptor jqlAesInterceptor = (JqlAesInterceptor)configurableListableBeanFactory.getBean(JqlAesInterceptor.class.getName());
            jqlAesInterceptor.setSqlAesProcessors(sqlAesProcessors);
        }
    }
}
