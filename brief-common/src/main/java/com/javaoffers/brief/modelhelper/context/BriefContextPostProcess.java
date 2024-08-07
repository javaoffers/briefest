package com.javaoffers.brief.modelhelper.context;

/**
 * brief上下文后置处理器. 不对外提供功能. BriefContext 处于处理过程中.
 * @author mingJie
 */
public interface BriefContextPostProcess {

    void postProcess(BriefContext briefContext);

}
