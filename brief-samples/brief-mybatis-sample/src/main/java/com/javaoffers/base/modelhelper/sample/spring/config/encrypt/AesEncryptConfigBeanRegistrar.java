package com.javaoffers.base.modelhelper.sample.spring.config.encrypt;

import com.javaoffers.base.modelhelper.sample.spring.config.encrypt.anno.AesEncryptConfig;
import com.javaoffers.brief.modelhelper.briefstate.BriefCommonComponentStates;
import com.javaoffers.brief.modelhelper.encrypt.*;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import com.javaoffers.brief.modelhelper.utils.Assert;

import java.util.ArrayList;

/**
 * @author mingJie
 */
public class AesEncryptConfigBeanRegistrar implements ImportBeanDefinitionRegistrar , BeanFactoryPostProcessor {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata
                .getAnnotationAttributes(AesEncryptConfig.class.getName()));
        //log.info("JdbcParameter tag: {}", new JdbcParameter().getTag());
        //私钥
        String key = attributes.getString(EncryptConfigConst.KEY);
        //私钥不允许为空
        Assert.isTrue(StringUtils.isNotBlank(key), "AES key is blank");
        //获取表字段信息(这对于这些信息进行加解密)
        AnnotationAttributes[] encryptTableColumns = attributes.getAnnotationArray("encryptTableColumns");

        if(encryptTableColumns != null){
            boolean status = false;
            EncryptConfigContext encryptConfigContext = new EncryptConfigContext();
            for(AnnotationAttributes encryptTableColumn : encryptTableColumns ){
                //表名称
                String tableName = encryptTableColumn.getString(EncryptConfigConst.TABLE_NAME);
                //表字段名称
                String[] columns = encryptTableColumn.getStringArray(EncryptConfigConst.COLUMNS);
                //将解析的信息缓存起来
                encryptConfigContext.addEncryptConfig(key, tableName, columns);
                BriefCommonComponentStates.ENCRYPT_STATE = status = true;
            }
            if(status){
                String beanName = "SqlAesProcessor#"+key;
                if(!registry.containsBeanDefinition(beanName)){
                    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(SqlAesProcessorImpl.class);
                    beanDefinitionBuilder.addConstructorArgValue(key);
                    beanDefinitionBuilder.addConstructorArgValue(encryptConfigContext);
                    registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());

                    BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(AesEncryptConfigBeanRegistrar.class);
                    registry.registerBeanDefinition(AesEncryptConfigBeanRegistrar.class.getName()+"#"+key, beanDefinition.getBeanDefinition());

                }
                //注册JqlAesInterceptor
                if(!registry.containsBeanDefinition(JqlAesInterceptor.class.getName())){
                    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(JqlAesInterceptorImpl.class);
                    registry.registerBeanDefinition(JqlAesInterceptorImpl.class.getName(), beanDefinitionBuilder.getBeanDefinition());
                }

                //注册myabtis拦截器
                if(!registry.containsBeanDefinition(JqlAesInterceptor.class.getName())){
                    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(JqlAesInterceptor.class);
                    registry.registerBeanDefinition(JqlAesInterceptor.class.getName(), beanDefinitionBuilder.getBeanDefinition());
                }

            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] beanNamesForType = configurableListableBeanFactory.getBeanNamesForType(SqlAesProcessorImpl.class);
        ArrayList<SqlAesProcessorImpl> sqlAesProcessors = Lists.newArrayList();
        if(beanNamesForType != null){
            for(String beanName : beanNamesForType){
                sqlAesProcessors.add((SqlAesProcessorImpl)configurableListableBeanFactory.getBean(beanName));
            }
        }
        JqlAesInterceptorImpl jqlAesInterceptor = (JqlAesInterceptorImpl)configurableListableBeanFactory.getBean(JqlAesInterceptorImpl.class.getName());
        jqlAesInterceptor.setSqlAesProcessors(sqlAesProcessors);
    }
}
