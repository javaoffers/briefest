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

import com.javaoffers.thrid.jsqlparser.JSQLParserException;
import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.parser.CCJSqlParserUtil;
import com.javaoffers.thrid.jsqlparser.schema.Table;
import com.javaoffers.thrid.jsqlparser.statement.select.*;

public final class SelectUtils {

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";

    private SelectUtils() {
    }

    public static Select buildSelectFromTableAndExpressions(Table table, Expression... expr) {
        SelectItem[] list = new SelectItem[expr.length];
        for (int i = 0; i < expr.length; i++) {
            list[i] = new SelectExpressionItem(expr[i]);
        }
        return buildSelectFromTableAndSelectItems(table, list);
    }

    public static Select buildSelectFromTableAndExpressions(Table table, String... expr) throws JSQLParserException {
        SelectItem[] list = new SelectItem[expr.length];
        for (int i = 0; i < expr.length; i++) {
            list[i] = new SelectExpressionItem(CCJSqlParserUtil.parseExpression(expr[i]));
        }
        return buildSelectFromTableAndSelectItems(table, list);
    }

    public static Select buildSelectFromTableAndSelectItems(Table table, SelectItem... selectItems) {
        PlainSelect body = new PlainSelect().addSelectItems(selectItems).withFromItem(table);
        return new Select().withSelectBody(body);
    }

    /**
     * Builds select * from table.
     *
     * @param table
     * @return
     */
    public static Select buildSelectFromTable(Table table) {
        return buildSelectFromTableAndSelectItems(table, new AllColumns());
    }

    /**
     * Adds an com.javaoffers.thrid.sqlparse.expression to select statements. E.g. a simple column is an com.javaoffers.thrid.sqlparse.expression.
     *
     * @param select
     * @param expr
     */
    public static void addExpression(Select select, final Expression expr) {
        if (select.getSelectBody() instanceof PlainSelect) {
            select.getSelectBody(PlainSelect.class).getSelectItems().add(new SelectExpressionItem(expr));
        } else {
            throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
        }
    }

    /**
     * Adds a simple join to a select com.javaoffers.thrid.sqlparse.statement. The introduced join is returned for more
     * configuration settings on it (e.g. left join, right join).
     *
     * @param select
     * @param table
     * @param onExpression
     * @return
     */
    public static Join addJoin(Select select, final Table table, final Expression onExpression) {
        if (select.getSelectBody() instanceof PlainSelect) {
            Join join = new Join().withRightItem(table).addOnExpression(onExpression);
            select.getSelectBody(PlainSelect.class).addJoins(join);
            return join;
        } else {
            throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
        }
    }

    /**
     * Adds group by to a plain select com.javaoffers.thrid.sqlparse.statement.
     *
     * @param select
     * @param expr
     */
    public static void addGroupBy(Select select, final Expression expr) {
        if (select.getSelectBody() instanceof PlainSelect) {
            select.getSelectBody(PlainSelect.class).addGroupByColumnReference(expr);
        } else {
            throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
        }
    }
}
