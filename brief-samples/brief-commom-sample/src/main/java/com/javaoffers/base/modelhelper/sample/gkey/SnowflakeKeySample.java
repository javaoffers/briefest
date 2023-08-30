package com.javaoffers.base.modelhelper.sample.gkey;

import com.javaoffers.brief.modelhelper.core.gkey.SnowflakeKey;
import com.javaoffers.brief.modelhelper.exception.BaseException;
import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author mingJie
 */
public class SnowflakeKeySample {
    SnowflakeKey snowflakeKey = new SnowflakeKey();
    ExecutorService executorService = Executors.newFixedThreadPool(100);


    //验证是否会有重复.
    @Test
    public void testMul() {

        ConcurrentHashMap<Long, Long> count = new ConcurrentHashMap<>();
        LinkedList<Future> futures = new LinkedList<>();

        futures.clear();
        long start = System.currentTimeMillis();
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
        long end = System.currentTimeMillis();
        System.out.println((end - start) +" ms : size: "+count.size());
        System.exit(1);
        //5754 ms : size: 10000000. (这里的耗时主要是 ConcurrentHashMap )
    }

    /**
     * 并发场景耗时.
     */
    @Test
    public void testMulThread(){
        long start = System.currentTimeMillis();
        LinkedList<Future> futures = new LinkedList<>();
        for(int i=0; i < 1000; i++){
            Future<?> submit = executorService.submit(() -> {
                int len = 10000;
                for(int j = 0 ; j < len; j++){
                    snowflakeKey.generate();
                }
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
        long end = System.currentTimeMillis();

        System.out.println((end - start) +" ms : size: "+ 10000000);
        System.exit(1);
        //2494 ms : size: 10000000 = 4009 (接近4096)
    }

    /**
     * 单线程场景耗时
     */
    @Test
    public void testSingle(){
        long start = System.currentTimeMillis();
        int len = 10000000;
        for(int i = 0 ; i < len; i++){
            snowflakeKey.generate();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) +" ms : size: "+len);
        //2466 ms : size: 10000000 = 4055 (接近4096)
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
