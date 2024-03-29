/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.expression.operators.relational;

import com.javaoffers.thrid.jsqlparser.statement.select.SubSelect;

public interface ItemsListVisitor {

    void visit(SubSelect subSelect);

    void visit(ExpressionList expressionList);

    void visit(NamedExpressionList namedExpressionList);

    void visit(MultiExpressionList multiExprList);
}
