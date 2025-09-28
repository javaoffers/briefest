package com.javaoffers.brief.modelhelper.filter;

import com.javaoffers.brief.modelhelper.core.BaseSQLStatement;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @description:
 * @author: create by cmj on 2023/5/28 19:29
 */
public class JqlExecutorChain<R> {

   private Function<BaseSQLStatement, R> supplier;

   private List<JqlExecutorFilter> filterList;

   private volatile int idx = 0;

   private BaseSQLStatement sqlStatement;

   private Class modelClass;

   private TableInfo tableInfo;

   public JqlExecutorChain(Function<BaseSQLStatement, R> supplier,
                           List<JqlExecutorFilter> filterList,
                           BaseSQLStatement sqlStatement, Class modelClass) {
      this.supplier = supplier;
      this.filterList = filterList;
      this.sqlStatement = sqlStatement;
      this.modelClass = modelClass;
      this.tableInfo = TableHelper.getTableInfo(modelClass);
   }

   public R doChain(){
      if(this.idx == filterList.size()){
         return doFinal();
      }
      JqlExecutorFilter filter = filterList.get(idx);
      ++idx;
      return (R)filter.filter(this);
   }

   public BaseSQLStatement getSqlStatement(){
      return this.sqlStatement;
   }

   private R doFinal(){
      return supplier.apply(sqlStatement);
   }

   public TableInfo getTableInfo() {
      return this.tableInfo;
   }
}
