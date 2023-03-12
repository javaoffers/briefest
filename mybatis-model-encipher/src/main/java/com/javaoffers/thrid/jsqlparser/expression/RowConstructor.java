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
import com.javaoffers.thrid.jsqlparser.statement.create.table.ColumnDefinition;

import java.util.ArrayList;

public class RowConstructor extends ASTNodeAccessImpl implements Expression {
    private ExpressionList exprList;
    private ArrayList<ColumnDefinition> columnDefinitions = new ArrayList<>();
    private String name = null;

    public RowConstructor() {
    }
    
    public ArrayList<ColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }
    
    public boolean addColumnDefinition(ColumnDefinition columnDefinition) {
        return columnDefinitions.add(columnDefinition);
    }

    public ExpressionList getExprList() {
        return exprList;
    }

    public void setExprList(ExpressionList exprList) {
        this.exprList = exprList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        if (columnDefinitions.size()>0) {
            StringBuilder builder = new StringBuilder(name != null ? name : "");
            builder.append("(");
            int i = 0;
            for (ColumnDefinition columnDefinition:columnDefinitions) {
                builder.append(i>0 ? ", " : "").append(columnDefinition.toString());
                i++;
            }
            builder.append(")");
            return builder.toString();
        }
        return (name != null ? name : "") + exprList.toString();
    }

    public RowConstructor withExprList(ExpressionList exprList) {
        this.setExprList(exprList);
        return this;
    }

    public RowConstructor withName(String name) {
        this.setName(name);
        return this;
    }
}
