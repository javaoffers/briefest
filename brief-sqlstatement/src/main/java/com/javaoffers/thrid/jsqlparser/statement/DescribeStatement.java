/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement;

import com.javaoffers.thrid.jsqlparser.schema.Table;

public class DescribeStatement implements Statement {

    private Table table;

    public DescribeStatement() {
        // empty constructor
    }

    public DescribeStatement(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "DESCRIBE " + table.getFullyQualifiedName();
    }

    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

    public DescribeStatement withTable(Table table) {
        this.setTable(table);
        return this;
    }
}
