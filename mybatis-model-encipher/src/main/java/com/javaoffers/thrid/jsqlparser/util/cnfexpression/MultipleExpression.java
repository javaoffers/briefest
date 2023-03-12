/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.cnfexpression;

import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.ExpressionVisitor;
import com.javaoffers.thrid.jsqlparser.expression.NullValue;
import com.javaoffers.thrid.jsqlparser.parser.ASTNodeAccessImpl;

import java.util.List;

/**
 * This is a helper class that mainly used for handling the CNF conversion.
 *
 * @author messfish
 *
 */
public abstract class MultipleExpression extends ASTNodeAccessImpl implements Expression {

    private final List<Expression> childlist;

    public MultipleExpression(List<Expression> childlist) {
        this.childlist = childlist;
    }

    public int size() {
        return childlist.size();
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(new NullValue());
    }

    public List<Expression> getList() {
        return childlist;
    }

    public Expression getChild(int index) {
        return childlist.get(index);
    }

    public Expression removeChild(int index) {
        return childlist.remove(index);
    }

    public void setChild(int index, Expression express) {
        childlist.set(index, express);
    }

    public int getIndex(Expression express) {
        return childlist.indexOf(express);
    }

    public void addChild(int index, Expression express) {
        childlist.add(index, express);
    }

    public abstract String getStringExpression();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < size(); i++) {
            sb.append(getChild(i));
            if (i != size() - 1) {
                sb.append(" ").append(getStringExpression()).append(" ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

}
