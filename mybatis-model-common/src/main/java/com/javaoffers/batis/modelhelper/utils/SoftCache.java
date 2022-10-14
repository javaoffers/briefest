package com.javaoffers.batis.modelhelper.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Released by GC when memory is not enough
 * @author mingJie
 */
public class SoftCache<T,V> {

    // soft ref
    ReferenceQueue<V> referenceQueue;

    //cache
    Map<T,SoftData<T,V>> cache = new ConcurrentHashMap<>();

    // new install of SoftCache
    public static <T,V> SoftCache<T,V> getInstance(){
        SoftCache<T,V> softCache = new SoftCache();
        softCache.referenceQueue = new ReferenceQueue<>();
        return softCache;
    }

    public void put(T key, V value){
        SoftData<T,V> softData = new SoftData<T,V>(key, value, this.referenceQueue);
        cache.put(key, softData);
    }

    public V get(T key){
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

    static class SoftData<T,V> extends SoftReference<V> {
        T key;
        public SoftData(T key,V value,ReferenceQueue<V> referenceQueue) {
            super(value, referenceQueue);
            this.key = key;
        }
    }
}
