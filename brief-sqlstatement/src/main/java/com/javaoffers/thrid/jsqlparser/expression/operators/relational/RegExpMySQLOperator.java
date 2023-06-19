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
import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.ExpressionVisitor;

import java.util.Objects;

public class RegExpMySQLOperator extends BinaryExpression {

    private RegExpMatchOperatorType operatorType;
    private boolean useRLike = false;
    private boolean not = false;

    public RegExpMySQLOperator(RegExpMatchOperatorType operatorType) {
        this(false, operatorType); 
    }
    
    public RegExpMySQLOperator(boolean not, RegExpMatchOperatorType operatorType) {
        this.operatorType = Objects.requireNonNull(operatorType, "The provided RegExpMatchOperatorType must not be NULL.");
        this.not = not;
    }

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public RegExpMatchOperatorType getOperatorType() {
        return operatorType;
    }

    public boolean isUseRLike() {
        return useRLike;
    }

    public RegExpMySQLOperator useRLike() {
        useRLike = true;
        return this;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String getStringExpression() {
        return (not?"NOT ":"") 
                + (useRLike ? "RLIKE" : "REGEXP")
                + (operatorType == RegExpMatchOperatorType.MATCH_CASESENSITIVE ? " BINARY" : "");
    }

    @Override
    public RegExpMySQLOperator withLeftExpression(Expression arg0) {
        return (RegExpMySQLOperator) super.withLeftExpression(arg0);
    }

    @Override
    public RegExpMySQLOperator withRightExpression(Expression arg0) {
        return (RegExpMySQLOperator) super.withRightExpression(arg0);
    }
}
