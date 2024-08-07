package com.javaoffers.brief.modelhelper.context;

/**
 * 有一些对象想使用BriefContext 对象中的信息,则可以实现该接口.
 * 此时的briefContext已经创建完毕.
 * @author mingJie
 */
public interface BriefContextAware {

    void setBriefContext(BriefContext briefContext);

}
