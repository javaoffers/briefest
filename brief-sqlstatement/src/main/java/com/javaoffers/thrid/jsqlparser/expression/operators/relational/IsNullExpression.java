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
import com.javaoffers.thrid.jsqlparser.parser.ASTNodeAccessImpl;

public class IsNullExpression extends ASTNodeAccessImpl implements Expression {

    private Expression leftExpression;
    private boolean not = false;
    private boolean useIsNull = false;

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public boolean isNot() {
        return not;
    }

    public void setLeftExpression(Expression expression) {
        leftExpression = expression;
    }

    public void setNot(boolean b) {
        not = b;
    }

    public boolean isUseIsNull() {
        return useIsNull;
    }

    public void setUseIsNull(boolean useIsNull) {
        this.useIsNull = useIsNull;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        if (isUseIsNull()) {
            return leftExpression + (not ? " NOT" : "") + " ISNULL";
        } else {
            return leftExpression + " IS " + (not ? "NOT " : "") + "NULL";
        }
    }

    public IsNullExpression withUseIsNull(boolean useIsNull) {
        this.setUseIsNull(useIsNull);
        return this;
    }

    public IsNullExpression withLeftExpression(Expression leftExpression) {
        this.setLeftExpression(leftExpression);
        return this;
    }

    public IsNullExpression withNot(boolean not) {
        this.setNot(not);
        return this;
    }

    public <E extends Expression> E getLeftExpression(Class<E> type) {
        return type.cast(getLeftExpression());
    }
}
