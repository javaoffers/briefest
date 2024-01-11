package com.javaoffers.brief.modelhelper.context;

import com.javaoffers.brief.modelhelper.filter.Filter;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;

import java.util.List;

/**
 * @description: brief 初始化配置信息.
 * @author: create by cmj on 2023/8/5 13:20
 */
public interface BriefProperties {

    /**
     * 添加配置信息
     *
     * @param key
     * @param value
     * @return
     */
    public BriefProperties put(String key, String value);

    /**
     * 刷新完成创建.
     */
    public void fresh();

}
