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
import com.javaoffers.thrid.jsqlparser.statement.create.table.ColDataType;
import com.javaoffers.thrid.jsqlparser.statement.select.OrderByElement;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class XMLSerializeExpr extends ASTNodeAccessImpl implements Expression {

    private Expression expression;
    private List<OrderByElement> orderByElements;
    private ColDataType dataType;

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public List<OrderByElement> getOrderByElements() {
        return orderByElements;
    }

    public void setOrderByElements(List<OrderByElement> orderByElements) {
        this.orderByElements = orderByElements;
    }

    public ColDataType getDataType() {
        return dataType;
    }

    public void setDataType(ColDataType dataType) {
        this.dataType = dataType;
    }
    
    @Override
    public String toString() {
        return "xmlserialize(xmlagg(xmltext(" + expression + ")"
                + (orderByElements != null ? " ORDER BY " + orderByElements.stream().map(item -> item.toString()).collect(joining(", ")) : "")
                + ") AS " + dataType + ")";
    }
}
