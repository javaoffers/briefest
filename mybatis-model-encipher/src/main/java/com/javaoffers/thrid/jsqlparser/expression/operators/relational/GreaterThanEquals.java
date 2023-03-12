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

import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.ExpressionVisitor;

public class GreaterThanEquals extends ComparisonOperator {

    public GreaterThanEquals() {
        super(">=");
    }

    public GreaterThanEquals(String operator) {
        super(operator);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public GreaterThanEquals withLeftExpression(Expression arg0) {
        return (GreaterThanEquals) super.withLeftExpression(arg0);
    }

    @Override
    public GreaterThanEquals withRightExpression(Expression arg0) {
        return (GreaterThanEquals) super.withRightExpression(arg0);
    }

    @Override
    public GreaterThanEquals withOldOracleJoinSyntax(int arg0) {
        return (GreaterThanEquals) super.withOldOracleJoinSyntax(arg0);
    }

    @Override
    public GreaterThanEquals withOraclePriorPosition(int arg0) {
        return (GreaterThanEquals) super.withOraclePriorPosition(arg0);
    }
}
