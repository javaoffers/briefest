package com.javaoffers.brief.modelhelper.speedier.transaction;

/**
 * @author mingJie
 */
public interface BriefTransaction {
    /**
     * 打开事务.
     */
    public void openTransaction();

    /**
     * 提交事务.
     */
    public void commitTransaction();

    /**
     * 回滚事务.
     */
    public void rollbackTransaction();
}
