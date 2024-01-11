package com.javaoffers.brief.modelhelper.jdbc.spring;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextPostProcess;
import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptorLoader;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 该类支持了spring的能力.
 * @author mingJie
 */
public class SpringContextPostProcess implements BriefContextPostProcess {

    ConfigurableListableBeanFactory beanFactory;

    SpringContextPostProcess() { }

    @Override
    public void postProcess(BriefContext briefContext) {
        SpringBriefContext springBriefContext = (SpringBriefContext) briefContext;
        beanFactory = springBriefContext.getBeanFactory();

        //加载spring容器中的 BriefContextPostProcess
        String[] beanNamesForBriefContextPostProcess = beanFactory.getBeanNamesForType(BriefContextPostProcess.class);
        if(beanNamesForBriefContextPostProcess==null || beanNamesForBriefContextPostProcess.length == 0){
            for(String beanName : beanNamesForBriefContextPostProcess){
                beanFactory.getBean(beanName,BriefContextPostProcess.class).postProcess(springBriefContext);
            }
        }

        //加载spring容器中的 JqlInterceptor
        String[] beanNamesForType = beanFactory.getBeanNamesForType(JqlInterceptor.class);
        if(beanNamesForType==null || beanNamesForType.length == 0){
            for(String beanName : beanNamesForType){
                springBriefContext.getJqlInterceptors().add(beanFactory.getBean(beanName,JqlInterceptor.class));
            }
        }
    }

}
