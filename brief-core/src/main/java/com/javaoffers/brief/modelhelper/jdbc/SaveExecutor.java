package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.core.SQL;

import java.util.List;

/**
 * @description: save
 * @author: create by cmj on 2023/7/29 18:26
 */
public interface SaveExecutor {

    Id save(SQL sql);

    List<Id> batchSave(SQL sql);

}
