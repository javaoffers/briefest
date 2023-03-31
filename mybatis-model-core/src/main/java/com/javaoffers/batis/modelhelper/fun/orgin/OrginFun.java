package com.javaoffers.batis.modelhelper.fun.orgin;

import java.util.Map;

/**
 * @author mingJie
 */
public interface OrginFun {

    Map<String, Object> selectOne(String sql, Map<String, Object> param);

    Map<String, Object> selectOne(String sql);


}
