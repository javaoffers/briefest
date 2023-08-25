package com.javaoffers.base.modelhelper.sample.gkey;

import com.javaoffers.brief.modelhelper.core.gkey.SnowflakeKey;
import com.javaoffers.brief.modelhelper.exception.BaseException;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author mingJie
 */
public class SnowflakeKeySample {

    public static void main(String[] args) {
        SnowflakeKey snowflakeKey = new SnowflakeKey();
        ConcurrentHashMap<Long, Long> count = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        LinkedList<Future> futures = new LinkedList<>();
        for(int i=0; i < 1000; i++){
            Future<?> submit = executorService.submit(() -> {
                countTest(snowflakeKey, count);
            });
            futures.add(submit);
        }
        futures.forEach(future -> {
            try {
                future.get();
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        System.out.println(count.size());
        System.exit(1);
    }

    private static void countTest(SnowflakeKey snowflakeKey, ConcurrentHashMap<Long, Long> count) {
        for(int i=0; i< 10000; i++){
            Long generate = snowflakeKey.generate();
            Long aLong = count.putIfAbsent(generate, generate);
            if(aLong != null){
                throw new BaseException("重复");
            }
        }
    }
}
