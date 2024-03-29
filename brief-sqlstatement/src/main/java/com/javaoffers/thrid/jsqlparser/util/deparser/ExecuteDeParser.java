/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.deparser;

import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.ExpressionVisitor;
import com.javaoffers.thrid.jsqlparser.statement.execute.Execute;

import java.util.List;

public class ExecuteDeParser extends AbstractDeParser<Execute> {

    private ExpressionVisitor expressionVisitor;

    public ExecuteDeParser(ExpressionVisitor expressionVisitor, StringBuilder buffer) {
        super(buffer);
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public void deParse(Execute execute) {
        buffer.append(execute.getExecType().name()).append(" ").append(execute.getName());
        if (execute.isParenthesis()) {
            buffer.append(" (");
        } else if (execute.getExprList() != null) {
            buffer.append(" ");
        }
        if (execute.getExprList() != null) {
            List<Expression> expressions = execute.getExprList().getExpressions();
            for (int i = 0; i < expressions.size(); i++) {
                if (i > 0) {
                    buffer.append(", ");
                }
                expressions.get(i).accept(expressionVisitor);
            }
        }
        if (execute.isParenthesis()) {
            buffer.append(")");
        }
    }

    public ExpressionVisitor getExpressionVisitor() {
        return expressionVisitor;
    }

    public void setExpressionVisitor(ExpressionVisitor visitor) {
        expressionVisitor = visitor;
    }
}
