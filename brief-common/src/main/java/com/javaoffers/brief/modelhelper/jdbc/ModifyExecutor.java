package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;

import java.util.List;

/**
 * @description: modify
 * @author: create by cmj on 2023/7/29 18:26
 */
public interface ModifyExecutor extends DataSourceExecutor{

    int modify(BaseSQLInfo sql);

    int batchModify(BaseSQLInfo sql);

}
