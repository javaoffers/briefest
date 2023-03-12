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
import com.javaoffers.thrid.jsqlparser.statement.select.OrderByElement;

import java.util.*;

public class KeepExpression extends ASTNodeAccessImpl implements Expression {

    private String name;
    private List<OrderByElement> orderByElements;
    private boolean first = false;

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    public List<OrderByElement> getOrderByElements() {
        return orderByElements;
    }

    public void setOrderByElements(List<OrderByElement> orderByElements) {
        this.orderByElements = orderByElements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();

        b.append("KEEP (").append(name);

        b.append(" ").append(first ? "FIRST" : "LAST").append(" ");
        toStringOrderByElements(b);

        b.append(")");

        return b.toString();
    }

    private void toStringOrderByElements(StringBuilder b) {
        if (orderByElements != null && !orderByElements.isEmpty()) {
            b.append("ORDER BY ");
            for (int i = 0; i < orderByElements.size(); i++) {
                if (i > 0) {
                    b.append(", ");
                }
                b.append(orderByElements.get(i).toString());
            }
        }
    }

    public KeepExpression withName(String name) {
        this.setName(name);
        return this;
    }

    public KeepExpression withOrderByElements(List<OrderByElement> orderByElements) {
        this.setOrderByElements(orderByElements);
        return this;
    }

    public KeepExpression withFirst(boolean first) {
        this.setFirst(first);
        return this;
    }

    public KeepExpression addOrderByElements(OrderByElement... orderByElements) {
        List<OrderByElement> collection = Optional.ofNullable(getOrderByElements()).orElseGet(ArrayList::new);
        Collections.addAll(collection, orderByElements);
        return this.withOrderByElements(collection);
    }

    public KeepExpression addOrderByElements(Collection<? extends OrderByElement> orderByElements) {
        List<OrderByElement> collection = Optional.ofNullable(getOrderByElements()).orElseGet(ArrayList::new);
        collection.addAll(orderByElements);
        return this.withOrderByElements(collection);
    }
}
