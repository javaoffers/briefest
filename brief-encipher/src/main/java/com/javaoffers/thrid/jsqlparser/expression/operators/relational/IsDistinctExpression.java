/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.expression.operators.relational;

import com.javaoffers.thrid.jsqlparser.expression.BinaryExpression;
import com.javaoffers.thrid.jsqlparser.expression.ExpressionVisitor;

public class IsDistinctExpression extends BinaryExpression {

    private boolean not = false;

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean b) {
        not = b;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String getStringExpression() {
        return " IS " + (isNot() ? "NOT " : "") + "DISTINCT FROM ";
    }

    @Override
    public String toString() {
        String retval = getLeftExpression() + getStringExpression() + getRightExpression();
        return retval;
    }
}
