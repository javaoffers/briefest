/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement.values;

import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.operators.relational.ExpressionList;
import com.javaoffers.thrid.jsqlparser.expression.operators.relational.ItemsList;
import com.javaoffers.thrid.jsqlparser.statement.Statement;
import com.javaoffers.thrid.jsqlparser.statement.StatementVisitor;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectBody;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectVisitor;

import java.util.ArrayList;
import java.util.Collection;

public class ValuesStatement implements Statement, SelectBody {

    private ItemsList expressions;

    public ValuesStatement() {
        // empty constructor
    }

    public ValuesStatement(ItemsList expressions) {
        this.expressions = expressions;
    }

    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

    public ItemsList getExpressions() {
        return expressions;
    }

    public void setExpressions(ItemsList expressions) {
        this.expressions = expressions;
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();
        sql.append("VALUES ");
        sql.append(expressions.toString());
        return sql.toString();
    }

    @Override
    public void accept(SelectVisitor selectVisitor) {
        selectVisitor.visit(this);
    }

    public ValuesStatement withExpressions(ItemsList expressions) {
        this.setExpressions(expressions);
        return this;
    }

    public ValuesStatement addExpressions(Expression... addExpressions) {
        if (expressions != null && expressions instanceof ExpressionList) {
            ((ExpressionList) expressions).addExpressions(addExpressions);
            return this;
        } else {
            return this.withExpressions(new ExpressionList(addExpressions));
        }
    }

    public ValuesStatement addExpressions(Collection<? extends Expression> addExpressions) {
        if (expressions != null && expressions instanceof ExpressionList) {
            ((ExpressionList) expressions).addExpressions(addExpressions);
            return this;
        } else {
            return this.withExpressions(new ExpressionList(new ArrayList<>(addExpressions)));
        }
    }
}
