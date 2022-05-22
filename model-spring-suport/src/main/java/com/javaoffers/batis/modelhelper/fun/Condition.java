package com.javaoffers.batis.modelhelper.fun;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: 条件借口
 * @Auther: create by cmj on 2022/5/2 02:22
 */
public interface Condition {

   public ConditionTag getConditionTag();

   public String getSql();

   public Map<String,Object> getParams();

   static AtomicLong idx = new AtomicLong(0);

   default long getNextLong(){
      long idxAndIncrement = idx.getAndIncrement();
      if(idxAndIncrement == Long.MAX_VALUE-10){
        for(;!idx.compareAndSet(idx.get(),0);){ }
      }
      return idxAndIncrement;
   }

}
