package com.javaoffers.batis.modelhelper.core;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 封装
 * @Auther: create by cmj on 2022/5/22 17:43
 */
@Data
@Builder
public class SQLInfo {
    Class aClass;
    String sql;
    List<Map<String,Object>> params = new LinkedList<>();

}
