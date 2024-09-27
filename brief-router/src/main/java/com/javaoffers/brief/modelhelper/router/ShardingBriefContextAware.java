package com.javaoffers.brief.modelhelper.router;

import com.javaoffers.brief.modelhelper.context.BriefContext;
import com.javaoffers.brief.modelhelper.context.BriefContextAware;
import com.javaoffers.brief.modelhelper.jdbc.JdbcExecutorFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 该类主要用于修改BriefContext组件.
 */
public class ShardingBriefContextAware implements BriefContextAware {
    private AtomicBoolean status = new AtomicBoolean(true);

    @Override
    public void setBriefContext(BriefContext briefContext) {
        if(status.get()){
            status.set(false);
            //将上下文替换为ShardingBriefContext
            new ShardingBriefContext(briefContext).fresh();
        }
    }
}
