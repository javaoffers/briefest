package com.javaoffers.brief.modelhelper.encrypt;

import com.javaoffers.brief.modelhelper.parser.TableColumns;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 加密的配置信息.
 * @author mingJie
 */

public class EncryptConfigContext {
    //key: 私钥, list: table信息(包含要加解密的字段信息)
    private static final Map<String, List<TableColumns>> keyTableColumns = new ConcurrentHashMap<>();

    public synchronized static void put(String key, String tableName, String[] columns){
        //log.info("aes key : {}, tableName : {}, columns: {}", key, tableName, columns);
        List<TableColumns> tableColumnsList = keyTableColumns.get(key);
        //如果list为空,则进行初始化
        if(tableColumnsList == null){
            tableColumnsList = Lists.newArrayList();
            TableColumns tableColumns = new TableColumns();
            tableColumns.setTableName(tableName);
            tableColumns.setColumns(Arrays.stream(columns).collect(Collectors.toSet()));
            tableColumnsList.add(tableColumns);
            keyTableColumns.put(key, tableColumnsList);
        }else{
            TableColumns oldTableColumns = null;
            for(TableColumns tableColumns : tableColumnsList){
                if(tableColumns.getTableName().equalsIgnoreCase(tableName)){
                    oldTableColumns = tableColumns;
                    break;
                }
            }
            //如果List中不存在则需要新建
            if(oldTableColumns == null){
                oldTableColumns = new TableColumns();
                oldTableColumns.setTableName(tableName);
                oldTableColumns.setColumns(Arrays.stream(columns).collect(Collectors.toSet()));
                tableColumnsList.add(oldTableColumns);
            }else{
                //如果存在则将新的字段信息进行添加进去
                oldTableColumns.getColumns().addAll(Arrays.stream(columns).collect(Collectors.toSet()));
            }
        }

        //校验,同一张表不允许配置多个KEY.(一个table对应一个key, 不支持多key配置)
        Collection<List<TableColumns>> values = keyTableColumns.values();
        Map<String, Integer> count = new TreeMap<>();
        if(values != null){
            values.stream().flatMap(tableColumnss -> {
                return  tableColumnss.stream();
            }).forEach(tableColumns -> {
                Integer c = count.getOrDefault(tableColumns.getTableName(), 0);
                c = c+1;
                Assert.isTrue(c == 1, "the-same "+tableColumns.getTableName()
                        +"The database table does not allow multiple KEYs to be configured. " +
                        "(One table corresponds to one key, and multiple key configurations are not supported)");
                count.put(tableColumns.getTableName(), c );
            });

        }
    }

    public synchronized static List<TableColumns> getTableColumsByKey(String key){
        List<TableColumns> tableColumns = keyTableColumns.get(key);
        if(tableColumns == null){
            tableColumns = Lists.newArrayList();
        }
        return tableColumns;
    }
}
