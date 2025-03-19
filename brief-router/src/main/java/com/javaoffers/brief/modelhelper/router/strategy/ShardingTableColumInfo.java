package com.javaoffers.brief.modelhelper.router.strategy;

import java.util.List;

/**
 * 分表解析的元数据.
 *
 * @author cao ming jie create by 2025/3/19
 */
public class ShardingTableColumInfo {
    //原始表明
    private String orgTableName;
    //解析的字段名
    private String colName;
    //colName对应的参数值
    private List<Object> params;


}
