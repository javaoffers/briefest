/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement.select;

import com.javaoffers.thrid.jsqlparser.expression.Alias;
import com.javaoffers.thrid.jsqlparser.expression.operators.relational.ExpressionList;

public class ExpressionListItem {

    private ExpressionList expressionList;
    private Alias alias;

    public ExpressionList getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(ExpressionList expressionList) {
        this.expressionList = expressionList;
    }

    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return expressionList + ((alias != null) ? alias.toString() : "");
    }

    public ExpressionListItem withExpressionList(ExpressionList expressionList) {
        this.setExpressionList(expressionList);
        return this;
    }

    public ExpressionListItem withAlias(Alias alias) {
        this.setAlias(alias);
        return this;
    }
}
