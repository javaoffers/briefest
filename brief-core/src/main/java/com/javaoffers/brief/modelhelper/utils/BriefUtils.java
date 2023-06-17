package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.core.CrudMapperMethodExcutor;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * create by cmj
 */
public class BriefUtils {

    private static HashMap<Method,String> isMapperMethod = new HashMap<>();

    private static HashMap<Method, String> isObjectMethod = new HashMap<>();

    static {
        Stream.of(
                BriefMapper.class.getDeclaredMethods()
        ).flatMap(Stream::of).forEach(method -> {
            method.setAccessible(true);
            isMapperMethod.put(method,method.getName());
        });

        Stream.of(
                Object.class.getDeclaredMethods()
        ).flatMap(Stream::of).forEach(method -> {
            method.setAccessible(true);
            isObjectMethod.put(method,method.getName());
        });
    }

    /**
     * Initialize crudMapper agent
     * @param crudMapperClass
     * @param <T>
     * @return
     */
    public static <T extends BriefMapper> T  newCrudMapper(Class<T> crudMapperClass) {
        ByteBuddyUtils.DefaultClass select = ByteBuddyUtils.buildDefaultClass(
                "select", CrudMapperMethodExcutor.class);

        ByteBuddyUtils.DefaultClass insert = ByteBuddyUtils.buildDefaultClass(
                "insert",CrudMapperMethodExcutor.class);
        ByteBuddyUtils.DefaultClass update = ByteBuddyUtils.buildDefaultClass(
                "update",CrudMapperMethodExcutor.class);
        ByteBuddyUtils.DefaultClass delete = ByteBuddyUtils.buildDefaultClass(
                "delete",CrudMapperMethodExcutor.class);
        ByteBuddyUtils.DefaultClass general = ByteBuddyUtils.buildDefaultClass(
                "general",CrudMapperMethodExcutor.class);

        return (T) ByteBuddyUtils
                .makeObject(crudMapperClass,
                        Arrays.asList(select,insert,update,delete,general));
    }

    public static Map<Method,String> getMapperMethod(){
        return Collections.unmodifiableMap(isMapperMethod);
    }

    public static Map<Method,String> getObjectMethod(){
        return Collections.unmodifiableMap(isObjectMethod);
    }
}
