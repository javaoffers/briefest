package com.javaoffers.brief.modelhelper.router;

/**
 * 分表策略.
 */
public enum ShardingTableType {

    /**
     * 按照年进行分表.
     */
    YEAR,

    /**
     * 按照月进行分表.
     */
    MONTH,

    /**
     * 按照天进行分表
     */
    DAY,

}
