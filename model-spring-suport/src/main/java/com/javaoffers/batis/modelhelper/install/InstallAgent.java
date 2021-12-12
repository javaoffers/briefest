package com.javaoffers.batis.modelhelper.install;

import com.javaoffers.batis.modelhelper.aggent.MapperProxyAggent;
import com.javaoffers.batis.modelhelper.aggent.TypeAliasRegistryAggent;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author cmj
 * @Description TODO
 * @createTime 2021年12月12日 20:22:00
 */
@Configuration
public class InstallAgent implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ByteBuddyAgent.install();

        new ByteBuddy()
                .redefine(MapperProxyAggent.class) //这种方式要保证   Bar 和 Foo 的schame相同
                .name(MapperProxy.class.getName())
                .make()
                .load(MapperProxy.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        String name = MapperProxy.class.getName();

        new ByteBuddy()
                .redefine(TypeAliasRegistryAggent.class) //这种方式要保证   Bar 和 Foo 的schame相同
                .name(TypeAliasRegistry.class.getName())
                .make()
                .load(TypeAliasRegistry.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }
}
