package com.javaoffers.batis.modelhelper.aggent;

import com.javaoffers.batis.modelhelper.exception.VersionException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import org.apache.ibatis.binding.*;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilderAggent;
import org.apache.ibatis.type.TypeAliasRegistry;

/**
 * install. Currently only supported My Batis3.4.1
 */
public class InstallModelHelper {

    private static final String MYBATIS_VERSION = "3.4.1";

    public static void install(){

        ByteBuddyAgent.install();

        install341();
    }

    private static void install341() {
        try {
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

            new ByteBuddy()
                    .redefine(MapperProxyFactoryAggent.class)
                    .name(MapperProxyFactory.class.getName())
                    .make()
                    .load(MapperProxyFactory.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        }catch (Exception e){
            throw new VersionException("This version is compatible with mybatis "+MYBATIS_VERSION+"," +
                    " please make sure that the version of mybatis is "+MYBATIS_VERSION);
        }

    }

}
