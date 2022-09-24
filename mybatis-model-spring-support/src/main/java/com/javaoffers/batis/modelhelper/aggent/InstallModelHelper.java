package com.javaoffers.batis.modelhelper.aggent;

import com.javaoffers.batis.modelhelper.exception.VersionException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperProxyFactoryAggent3511;
import org.apache.ibatis.binding.MapperProxyFactoryAggent341;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * install. Currently only supported My Batis3.4.1
 */
public class InstallModelHelper {

    private static final String[] MYBATIS_VERSION = new String[]{"3.4.1"};

    public static void install(){

        ByteBuddyAgent.install();

        if(install341()){}
        else if(install3511()){}
        else {
            throw new VersionException("This version is compatible with mybatis "+ Arrays.toString(MYBATIS_VERSION)+"," +
                    " please make sure that the version of mybatis is "+Arrays.toString(MYBATIS_VERSION));
        }
    }

    private static boolean install3511() {
        return install(MapperProxyFactoryAggent3511.class);
    }

    private static boolean install341() {
        return install(MapperProxyFactoryAggent341.class);
    }

    private static boolean install(Class mapperProxyFactoryAggent) {
        try {
            new ByteBuddy()
                    .redefine(mapperProxyFactoryAggent)
                    .name(MapperProxyFactory.class.getName())
                    .make()
                    .load(MapperProxyFactory.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        }catch (Exception e){

            return false;
        }
        return true;
    }

}
