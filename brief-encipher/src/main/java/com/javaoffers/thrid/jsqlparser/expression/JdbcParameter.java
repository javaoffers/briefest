/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.expression;

import com.javaoffers.thrid.jsqlparser.parser.ASTNodeAccessImpl;

/**
 * A '?' in a com.javaoffers.thrid.sqlparse.statement or a ?&lt;number&gt; e.g. ?4
 */
public class JdbcParameter extends ASTNodeAccessImpl implements Expression {

    private Integer index;
    private boolean useFixedIndex = false;
    private String tag = "?";

    public JdbcParameter() {
    }

    public JdbcParameter(Integer index, boolean useFixedIndex) {
        this.index = index;
        this.useFixedIndex = useFixedIndex;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public boolean isUseFixedIndex() {
        return useFixedIndex;
    }

    public void setUseFixedIndex(boolean useFixedIndex) {
        this.useFixedIndex = useFixedIndex;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return useFixedIndex ? tag + index : tag;
    }

    public JdbcParameter withIndex(Integer index) {
        this.setIndex(index);
        return this;
    }

    public JdbcParameter withUseFixedIndex(boolean useFixedIndex) {
        this.setUseFixedIndex(useFixedIndex);
        return this;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
