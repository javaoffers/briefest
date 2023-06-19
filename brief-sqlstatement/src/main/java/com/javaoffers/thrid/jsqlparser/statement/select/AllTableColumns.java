/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement.select;

import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.ExpressionVisitor;
import com.javaoffers.thrid.jsqlparser.parser.ASTNodeAccessImpl;
import com.javaoffers.thrid.jsqlparser.schema.Table;

public class AllTableColumns extends ASTNodeAccessImpl implements SelectItem, Expression {

    private Table table;

    public AllTableColumns() {
    }

    public AllTableColumns(Table tableName) {
        this.table = tableName;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public void accept(SelectItemVisitor selectItemVisitor) {
        selectItemVisitor.visit(this);
    }

    @Override
    public String toString() {
        return table + ".*";
    }

    public AllTableColumns withTable(Table table) {
        this.setTable(table);
        return this;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
