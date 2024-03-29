/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement.alter.sequence;

import com.javaoffers.thrid.jsqlparser.schema.Sequence;
import com.javaoffers.thrid.jsqlparser.statement.Statement;
import com.javaoffers.thrid.jsqlparser.statement.StatementVisitor;

/**
 * An {@code ALTER SEQUENCE} com.javaoffers.thrid.sqlparse.statement
 */
public class AlterSequence implements Statement {

    public Sequence sequence;

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Sequence getSequence() {
        return sequence;
    }

    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

    @Override
    public String toString() {
        String sql;
        sql = "ALTER SEQUENCE " + sequence;
        return sql;
    }

    public AlterSequence withSequence(Sequence sequence) {
        this.setSequence(sequence);
        return this;
    }
}
