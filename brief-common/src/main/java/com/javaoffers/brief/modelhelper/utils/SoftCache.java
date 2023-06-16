package com.javaoffers.brief.modelhelper.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Released by GC when memory is not enough
 * @author mingJie
 */
public class SoftCache<T,V> implements Runnable{

    private static int limitMi = 1 << 11;

    // soft ref
    private ReferenceQueue<V> referenceQueue;

    //cache
    private volatile Map<T,SoftData<T,V>> cache = new ConcurrentHashMap<>(1 << 8);

    // new install of SoftCache
    public static <T,V> SoftCache<T,V> getInstance(){

        SoftCache<T,V> softCache = new SoftCache();
        softCache.referenceQueue = new ReferenceQueue<>();
        CommonExecutor.oneFixedScheduledThreadPool.scheduleWithFixedDelay(softCache, 60, 60, TimeUnit.SECONDS);
        return softCache;
    }

    public void put(T key, V value){
        //key = null;
        if(key == null || value == null){return;}
        SoftData<T,V> softData = new SoftData<T,V>(key, value, this.referenceQueue);
        cache.put(key, softData);
    }

    public V get(T key){
        //key = null;
        if(key == null){
            return null;
        }
        SoftData<T,V> softData = cache.get(key);
        if(softData != null){
            V value = softData.get();
            if(value != null){
                return value;
            }else{
                //clear outdated
                cache.remove(key);
            }
        }
        return null;
    }

    @Override
    public void run() {
        try {
            long mk = Runtime.getRuntime().freeMemory() >> 20;
            AtomicBoolean statue = new AtomicBoolean(false);
            if(limitMi < mk){
                Map<T,SoftData<T,V>> newCache = new ConcurrentHashMap<>(1 << 8);
                this.cache.forEach((key,value)->{
                    if(value.get() != null){
                        newCache.put(key, value);
                    }else{
                        statue.set(true);
                    }
                });

                if(statue.get()) {
                    this.cache = newCache;
                    this.referenceQueue = new ReferenceQueue<>();
                }

            }else{
                this.cache.forEach((key,value)->{
                    if(value.get() == null){
                        this.cache.remove(key);
                        statue.set(true);
                    }
                });
                if(statue.get()) {
                    this.referenceQueue = new ReferenceQueue<>();
                }
            }
        }catch (Exception e){
            //ignore
        }
    }

    static class SoftData<T,V> extends SoftReference<V> {
        T key;
        public SoftData(T key,V value,ReferenceQueue<V> referenceQueue) {
            super(value, referenceQueue);
            this.key = key;
        }
    }

    private SoftCache(){ }
}
