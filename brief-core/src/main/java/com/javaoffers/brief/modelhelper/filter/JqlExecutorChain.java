package com.javaoffers.brief.modelhelper.filter;

import java.util.List;
import java.util.function.Supplier;

/**
 * @description:
 * @author: create by cmj on 2023/5/28 19:29
 */
public abstract class JqlExecutorChain<R,S> {

   private Supplier<R> supplier;

   private List<JqlExecutorFilter<R,S>> filterList;

   private volatile int idx = 0;


   public JqlExecutorChain(Supplier<R> supplier, List<JqlExecutorFilter<R, S>> filterList) {
      this.supplier = supplier;
      this.filterList = filterList;
   }

   public R doChain(S s){
      if(this.idx == filterList.size()){
         return doFinal();
      }
      JqlExecutorFilter<R,S> filter = filterList.get(idx);
      ++idx;
      return filter.filter(this);
   }

   private R doFinal(){
      return supplier.get();
   }

}
