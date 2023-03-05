package com.javaoffers.batis.modelhelper.encrypt;


import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author mingJie
 */

public class NamingContent {

    //是否是子查询
    private boolean isSubSelect;

    //如果是子查询，那么子查询的主表将被记录
    private String subSelectMainTable;

    //是否是join语句(多表查询).
    private boolean havaJoin;

    //key: 别名, value: 表名
    private Map<String, String> tableNameMapper = new HashMap<>();

    //key: realTableName, Set: colNames, consumer: 用于处理colNames
    private  Map<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>>  processor = new HashMap<>();

    public void putAliasNameAndRealTableName(String aliasName, String realTableName){
        //注意这里不排除空"".
        if(aliasName == null || realTableName == null){
            return;
        }
        tableNameMapper.put(aliasName, realTableName);
    }

    public boolean isContainsProcessorRealTableName(){
         return this.tableNameMapper.values().containsAll(this.processor.keySet());
    }

    public boolean isProcessorTableName(String realTableName){
       return processor.containsKey(realTableName);
    }

    public boolean isProcessorCloName(String realTableName, String colName){
          return processor.getOrDefault(realTableName, Pair.of(Collections.emptySet(), null)).getLeft().contains(colName);
    }

    public Consumer<ColNameProcessorInfo> getProcessorByTableName(String realTableName){
        Pair<Set<String>, Consumer<ColNameProcessorInfo>> processorColName = processor.get(realTableName);
        Assert.isTrue(processorColName != null , "processorColName is null");
        return processorColName.getRight();
    }

    public String getSimpleSingleTable(){
        Assert.isTrue(tableNameMapper.size() == 1, "not single table");
        return tableNameMapper.keySet().toArray(new String[]{})[0];
    }


    public Map<String, String> getTableNameMapper() {
        return this.tableNameMapper;
    }

    public boolean isHavaJoin() {
        return this.havaJoin;
    }

    public boolean isSubSelect() {
        return this.isSubSelect;
    }

    public String getSubSelectMainTable() {
        return this.subSelectMainTable;
    }

    public void setSubSelect(boolean isSubSelect) {
        this.isSubSelect = isSubSelect;
    }

    public Map<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>> getProcessor() {
        return processor;
    }

    public void setProcessor(Map<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>> processor) {
        this.processor = processor;
    }

    public void setSubSelectMainTable(String name) {
        this.subSelectMainTable = name;
    }

    public void setTableNameMapper(Map<String, String> tableNameMapper) {
        this.tableNameMapper = tableNameMapper;
    }

    public void setHavaJoin(boolean havaJoin) {
        this.havaJoin = havaJoin;
    }
}
