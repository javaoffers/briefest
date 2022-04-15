package com.javaoffers.batis.modelhelper.aggent;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.binding.MapperMethodAggent;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilderAggent;
import org.apache.ibatis.type.TypeAliasRegistry;

/**
 * install
 */
public class InstallModelHelper {

    public static void install(){
        ByteBuddyAgent.install();
        new ByteBuddy()
                .redefine(MapperMethodAggent.class)
                .name(MapperMethod.class.getName())
                .make()
                .load(MapperMethod.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        new ByteBuddy()
                .redefine(MapperProxyAggent.class)
                .name(MapperProxy.class.getName())
                .make()
                .load(MapperProxy.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        new ByteBuddy()
                .redefine(TypeAliasRegistryAggent.class)
                .name(TypeAliasRegistry.class.getName())
                .make()
                .load(TypeAliasRegistry.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());


        new ByteBuddy()
                .redefine(MapperAnnotationBuilderAggent.class)
                .name(MapperAnnotationBuilder.class.getName())
                .make()
                .load(MapperAnnotationBuilder.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }

}
