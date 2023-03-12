/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.validation.validator;

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
import com.javaoffers.thrid.jsqlparser.statement.select.SubSelect;
import com.javaoffers.thrid.jsqlparser.statement.show.*;
import com.javaoffers.thrid.jsqlparser.statement.truncate.*;
import com.javaoffers.thrid.jsqlparser.statement.update.*;
import com.javaoffers.thrid.jsqlparser.statement.upsert.*;
import com.javaoffers.thrid.jsqlparser.statement.values.*;

/**
 * @author gitmotte
 */
public class ItemsListValidator extends AbstractValidator<ItemsList> implements ItemsListVisitor {

    @Override
    public void visit(SubSelect subSelect) {
        validateOptionalFromItem(subSelect);
    }

    @Override
    public void visit(ExpressionList expressionList) {
        validateOptionalExpressions(expressionList.getExpressions());
    }

    @Override
    public void visit(NamedExpressionList namedExpressionList) {
        validateOptionalExpressions(namedExpressionList.getExpressions());
    }

    @Override
    public void visit(MultiExpressionList multiExprList) {
        multiExprList.getExpressionLists().forEach(l -> l.accept(this));
    }

    @Override
    public void validate(ItemsList statement) {
        statement.accept(this);
    }

}
