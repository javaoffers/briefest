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
 * Extract value from date/time com.javaoffers.thrid.sqlparse.expression. The name stores the part - name to get from the
 * following date/time com.javaoffers.thrid.sqlparse.expression.
 *
 * @author tw
 */
public class ExtractExpression extends ASTNodeAccessImpl implements Expression {

    private String name;
    private Expression expression;

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "EXTRACT(" + name + " FROM " + expression + ')';
    }

    public ExtractExpression withName(String name) {
        this.setName(name);
        return this;
    }

    public ExtractExpression withExpression(Expression expression) {
        this.setExpression(expression);
        return this;
    }

    public <E extends Expression> E getExpression(Class<E> type) {
        return type.cast(getExpression());
    }
}
