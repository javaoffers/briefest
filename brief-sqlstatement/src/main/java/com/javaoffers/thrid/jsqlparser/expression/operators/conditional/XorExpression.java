/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2021 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.expression.operators.conditional;

import com.javaoffers.thrid.jsqlparser.expression.BinaryExpression;
import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.ExpressionVisitor;

public class XorExpression extends BinaryExpression {

    public XorExpression() {
        // nothing
    }

    public XorExpression(Expression leftExpression, Expression rightExpression) {
        setLeftExpression(leftExpression);
        setRightExpression(rightExpression);
    }

    @Override
    public XorExpression withLeftExpression(Expression expression) {
        return (XorExpression) super.withLeftExpression(expression);
    }

    @Override
    public XorExpression withRightExpression(Expression expression) {
        return (XorExpression) super.withRightExpression(expression);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String getStringExpression() {
        return "XOR";
    }

}
