package com.javaoffers.brief.modelhelper.filter;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @description:
 * @author: create by cmj on 2023/5/28 19:29
 */
public class JqlExecutorChain<R> {

   private Function<JqlMetaInfo, R> supplier;

   private List<JqlExecutorFilter> filterList;

   private volatile int idx = 0;

   private JqlMetaInfo jqlMetaInfo;

   public JqlExecutorChain(Function<JqlMetaInfo, R> supplier,
                           List<JqlExecutorFilter> filterList,
                           JqlMetaInfo jqlMetaInfo) {
      this.supplier = supplier;
      this.filterList = filterList;
      this.jqlMetaInfo = jqlMetaInfo;
   }

   public R doChain(){
      if(this.idx == filterList.size()){
         return doFinal();
      }
      JqlExecutorFilter filter = filterList.get(idx);
      ++idx;
      return (R)filter.filter(this);
   }

   public JqlMetaInfo getJqlMetaInfo(){
      return this.jqlMetaInfo;
   }

   private R doFinal(){
      return supplier.apply(jqlMetaInfo);
   }

}
