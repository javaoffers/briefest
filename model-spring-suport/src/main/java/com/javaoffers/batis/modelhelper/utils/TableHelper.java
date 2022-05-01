package com.javaoffers.batis.modelhelper.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 表信息辅助类
 * @Auther: create by cmj on 2022/5/2 02:05
 */
public class TableHelper {

    /**
     * 数据库
     */
    private DataSource connection;
    /**
     * 获取 Model 对应的全部字段
     * @param modelClss
     * @return
     */
    public static List<String> getColAll(Class<?> modelClss){

        return Arrays.asList("");
    }
}
