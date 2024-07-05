package com.javaoffers.brief.modelhelper.utils;

/**
 * @author mingJie
 */
public enum SQLType {

    /**
     * JOIN QUERY.
     */
    JOIN_SELECT,
    /**
     * Ordinary query
     */
    NORMAL_SELECT,

    /**
     * DML: for example
     * CREATE
     * ALTER
     * DROP
     * TRUNCATE
     * COMMENT
     * RENAME
     */
    DDL,

    /**
     * DML: for example
     * EXPLAIN PLAN
     */
    DML,
}
