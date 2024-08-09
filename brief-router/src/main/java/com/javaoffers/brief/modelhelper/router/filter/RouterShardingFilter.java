package com.javaoffers.brief.modelhelper.router.filter;

import com.javaoffers.brief.modelhelper.filter.JqlExecutorChain;
import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
import com.javaoffers.brief.modelhelper.filter.JqlMetaInfo;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

public class RouterShardingFilter implements JqlExecutorFilter  {
    @Override
    public Object filter(JqlExecutorChain jqlExecutorChain) {
        JqlMetaInfo jqlMetaInfo = jqlExecutorChain.getJqlMetaInfo();
        TableInfo tableInfo = jqlMetaInfo.getTableInfo();
        //做分表查询
        return jqlExecutorChain.doChain();
    }
}
