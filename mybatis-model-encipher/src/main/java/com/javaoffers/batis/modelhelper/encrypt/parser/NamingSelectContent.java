package com.javaoffers.batis.modelhelper.encrypt.parser;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * sql的上下文数据, 可以从这个实例中得知当前sql的状态信息
 * @author mingJie
 */

public class NamingSelectContent {

    /**--------------------select-----------------**/
    //是否是子查询
    private boolean isSubSelct;

    //如果是子查询，那么子查询的主表将被记录
    private String subSelectMainTable;

    //是否是join语句(多表查询).
    private boolean havaJoin;

    //key: 别名, value: 表名
    private Map<String, String> tableNameMapper = new HashMap<>();

    /**--------------------insert-----------------**/
    // insert 字段的个数
    private int insertColLen;
    //插入的表名
    private String insertTableName;
    //需要加密的字段对应的下标为true
    private boolean[] needEncryptColMark;
    //遍历时记录当前的下标
    private int needEncryptColMarkIndex = 0;

    /**-------------------update------------------**/
    private String updateTableName;

    /**-------------------delete------------------**/
    private String deleteTableName;

    //key: realTableName, Set: colNames, consumer: 用于处理colNames
    private  Map<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>>  processor = new HashMap<>();

    public void putAliasNameAndRealTableName(String aliasName, String realTableName){
        //注意这里不排除空"".
        if(aliasName == null || realTableName == null){
            return;
        }
        tableNameMapper.put(aliasName, realTableName);
    }

    //是否存在要处理的table表. case: 多表关联, 并且where column中没有指定具体表名
    public boolean isContainsProcessorRealTableName(){
        return this.tableNameMapper.values().stream().anyMatch(tableName->{
            return isContains(this.processor.keySet(), tableName);
        });
    }

    //是否是要拦截处理的table表
    public boolean isProcessorTableName(String realTableName){
        return isContains(processor.keySet(), realTableName);
    }

    private boolean isContains(Collection<String> ls, String str){
        boolean status = false;
        for(String n : ls){
            if(!status){
                status =  n.equalsIgnoreCase(str);
            }
        }
        return status;
    }

    private String isContainsGetKey(Collection<String> ls, String str){
        boolean status = false;
        String key = str;
        for(String n : ls){
            if(!status){
                status =  n.equalsIgnoreCase(str);
                if(status){
                    key = n;
                }
            }
        }
        return key;
    }

    /**
     * 是否是拦截table表中要处理的字段
     * @param realTableName 要拦截处理的表
     * @param colName 表中要处理的字段
     * @return true. 需要处理
     */
    public boolean isProcessorCloName(String realTableName, String colName){
        realTableName = isContainsGetKey(processor.keySet(), realTableName);
        Set<String> left = processor.getOrDefault(realTableName, Pair.of(Collections.emptySet(), null)).getLeft();
        return isContains(left, colName);
    }

    /**
     * 使用该方法时,需要用 {@code isProcessorCloName} 进行一次判断.
     */
    public Consumer<ColNameProcessorInfo> getProcessorByTableName(String realTableName){
        realTableName = isContainsGetKey(processor.keySet(), realTableName);
        Pair<Set<String>, Consumer<ColNameProcessorInfo>> processorColName = processor.get(realTableName);
        Assert.isTrue(processorColName != null , "processorColName is null");
        return processorColName.getRight();
    }

    public String getSimpleSingleTable(){
        Assert.isTrue(tableNameMapper.size() == 1, "not single table");
        return tableNameMapper.values().toArray(new String[]{})[0];
    }

    public boolean isSubSelct() {
        return isSubSelct;
    }

    public void setSubSelct(boolean subSelct) {
        isSubSelct = subSelct;
    }

    public String getSubSelectMainTable() {
        return subSelectMainTable;
    }

    public void setSubSelectMainTable(String subSelectMainTable) {
        this.subSelectMainTable = subSelectMainTable;
    }

    public boolean isHavaJoin() {
        return havaJoin;
    }

    public void setHavaJoin(boolean havaJoin) {
        this.havaJoin = havaJoin;
    }

    public Map<String, String> getTableNameMapper() {
        return tableNameMapper;
    }

    public void setTableNameMapper(Map<String, String> tableNameMapper) {
        this.tableNameMapper = tableNameMapper;
    }

    public int getInsertColLen() {
        return insertColLen;
    }

    public void setInsertColLen(int insertColLen) {
        this.insertColLen = insertColLen;
    }

    public String getInsertTableName() {
        return insertTableName;
    }

    public void setInsertTableName(String insertTableName) {
        this.insertTableName = insertTableName;
    }

    public boolean[] getNeedEncryptColMark() {
        return needEncryptColMark;
    }

    public void setNeedEncryptColMark(boolean[] needEncryptColMark) {
        this.needEncryptColMark = needEncryptColMark;
    }

    public int getNeedEncryptColMarkIndex() {
        return needEncryptColMarkIndex;
    }

    public void setNeedEncryptColMarkIndex(int needEncryptColMarkIndex) {
        this.needEncryptColMarkIndex = needEncryptColMarkIndex;
    }

    public String getUpdateTableName() {
        return updateTableName;
    }

    public void setUpdateTableName(String updateTableName) {
        this.updateTableName = updateTableName;
    }

    public String getDeleteTableName() {
        return deleteTableName;
    }

    public void setDeleteTableName(String deleteTableName) {
        this.deleteTableName = deleteTableName;
    }

    public Map<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>> getProcessor() {
        return processor;
    }

    public void setProcessor(Map<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>> processor) {
        this.processor = processor;
    }
}
