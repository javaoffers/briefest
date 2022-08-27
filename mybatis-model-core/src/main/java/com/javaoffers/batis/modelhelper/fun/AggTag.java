package com.javaoffers.batis.modelhelper.fun;

/**
 * @Description: 聚合函数标签（支持单参数函数）
 * @Auther: create by cmj on 2022/6/5 17:17
 */
public enum AggTag {
    /**聚合函数**/
    AVG,//(表达式) 返回表达式中所有的平均值。仅用于数字列并自动忽略NULL值。
    COUNT,//(表达式) 返回表达式中非NULL值的数量。可用于数字和字符列。
    MAX,//(表达式) 返回表达式中的最大值,忽略NULL值。可用于数字、字符和日期时间列。
    MIN,//(表达式) 返回表达式中的最小值,忽略NULL值。可用于数字、字符和日期时间列。
    SUM,//(表达式) 返回表达式中所有的总和,忽略NULL值。仅用于数字列。
}
