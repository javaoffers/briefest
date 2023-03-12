/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util;

import com.javaoffers.thrid.jsqlparser.expression.*;
import com.javaoffers.thrid.jsqlparser.expression.operators.arithmetic.*;
import com.javaoffers.thrid.jsqlparser.expression.operators.conditional.*;
import com.javaoffers.thrid.jsqlparser.expression.operators.relational.*;
import com.javaoffers.thrid.jsqlparser.parser.feature.*;
import com.javaoffers.thrid.jsqlparser.schema.*;
import com.javaoffers.thrid.jsqlparser.statement.*;
import com.javaoffers.thrid.jsqlparser.statement.alter.*;
import com.javaoffers.thrid.jsqlparser.statement.alter.sequence.*;
import com.javaoffers.thrid.jsqlparser.statement.create.schema.*;
import com.javaoffers.thrid.jsqlparser.statement.create.sequence.*;
import com.javaoffers.thrid.jsqlparser.statement.create.synonym.*;
import com.javaoffers.thrid.jsqlparser.statement.create.table.*;
import com.javaoffers.thrid.jsqlparser.statement.create.view.*;
import com.javaoffers.thrid.jsqlparser.statement.delete.*;
import com.javaoffers.thrid.jsqlparser.statement.drop.*;
import com.javaoffers.thrid.jsqlparser.statement.execute.*;
import com.javaoffers.thrid.jsqlparser.statement.grant.*;
import com.javaoffers.thrid.jsqlparser.statement.insert.*;
import com.javaoffers.thrid.jsqlparser.statement.merge.*;
import com.javaoffers.thrid.jsqlparser.statement.replace.*;
import com.javaoffers.thrid.jsqlparser.statement.select.*;
import com.javaoffers.thrid.jsqlparser.statement.show.*;
import com.javaoffers.thrid.jsqlparser.statement.truncate.*;
import com.javaoffers.thrid.jsqlparser.statement.update.*;
import com.javaoffers.thrid.jsqlparser.statement.upsert.*;
import com.javaoffers.thrid.jsqlparser.statement.values.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Connect all selected expressions with a binary com.javaoffers.thrid.sqlparse.expression. Out of select a,b from table one gets
 * select a || b as expr from table. The type of binary com.javaoffers.thrid.sqlparse.expression is set by overwriting this class
 * abstract method createBinaryExpression.
 *
 * @author tw
 */
@SuppressWarnings({"PMD.UncommentedEmptyMethodBody"})
public abstract class ConnectExpressionsVisitor implements SelectVisitor, SelectItemVisitor {

    private String alias = "expr";
    private final List<SelectExpressionItem> itemsExpr = new LinkedList<SelectExpressionItem>();

    public ConnectExpressionsVisitor() {
    }

    public ConnectExpressionsVisitor(String alias) {
        this.alias = alias;
    }

    protected abstract BinaryExpression createBinaryExpression();

    @Override
    public void visit(PlainSelect plainSelect) {
        for (SelectItem item : plainSelect.getSelectItems()) {
            item.accept(this);
        }

        if (itemsExpr.size() > 1) {
            BinaryExpression binExpr = createBinaryExpression();
            binExpr.setLeftExpression(itemsExpr.get(0).getExpression());
            for (int i = 1; i < itemsExpr.size() - 1; i++) {
                binExpr.setRightExpression(itemsExpr.get(i).getExpression());
                BinaryExpression binExpr2 = createBinaryExpression();
                binExpr2.setLeftExpression(binExpr);
                binExpr = binExpr2;
            }
            binExpr.setRightExpression(itemsExpr.get(itemsExpr.size() - 1).getExpression());

            SelectExpressionItem sei = new SelectExpressionItem();
            sei.setExpression(binExpr);

            plainSelect.getSelectItems().clear();
            plainSelect.getSelectItems().add(sei);
        }

        ((SelectExpressionItem) plainSelect.getSelectItems().get(0)).setAlias(new Alias(alias));
    }

    @Override
    public void visit(SetOperationList setOpList) {
        for (SelectBody select : setOpList.getSelects()) {
            select.accept(this);
        }
    }

    @Override
    public void visit(WithItem withItem) {
    }

    @Override
    public void visit(AllTableColumns allTableColumns) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(AllColumns allColumns) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        itemsExpr.add(selectExpressionItem);
    }

    @Override
    public void visit(ValuesStatement aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
