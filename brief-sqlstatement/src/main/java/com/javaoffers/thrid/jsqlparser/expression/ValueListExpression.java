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

import com.javaoffers.thrid.jsqlparser.expression.operators.relational.ExpressionList;
import com.javaoffers.thrid.jsqlparser.parser.ASTNodeAccessImpl;

/**
 * Models a list of expressions usable as condition.<br>
 * This allows for instance the following com.javaoffers.thrid.sqlparse.expression :
 * <code>"[WHERE] (a, b) [OPERATOR] (c, d)"</code>
 * where "(a, b)" and "(c, d)" are instances of this class.
 */
public class ValueListExpression extends ASTNodeAccessImpl implements Expression {

    private ExpressionList expressionList;
    
    public ExpressionList getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(ExpressionList expressionList) {
        this.expressionList = expressionList;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
    
    @Override
    public String toString() {
        return expressionList.toString();
    }

    public ValueListExpression withExpressionList(ExpressionList expressionList) {
        this.setExpressionList(expressionList);
        return this;
    }
}
