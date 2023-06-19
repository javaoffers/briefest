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

import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.operators.relational.ExpressionList;
import com.javaoffers.thrid.jsqlparser.parser.feature.Feature;
import com.javaoffers.thrid.jsqlparser.statement.select.GroupByElement;
import com.javaoffers.thrid.jsqlparser.statement.select.GroupByVisitor;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationCapability;

/**
 * @author gitmotte
 */
public class GroupByValidator extends AbstractValidator<GroupByElement> implements GroupByVisitor {

    @Override
    public void validate(GroupByElement groupBy) {
        groupBy.accept(this);
    }

    @Override
    public void visit(GroupByElement groupBy) {
        for (ValidationCapability c : getCapabilities()) {
            validateFeature(c, Feature.selectGroupBy);
            if (isNotEmpty(groupBy.getGroupingSets())) {
                validateFeature(c, Feature.selectGroupByGroupingSets);
            }
        }

        validateOptionalExpressions(groupBy.getGroupByExpressions());

        if (isNotEmpty(groupBy.getGroupingSets())) {
            for (Object o : groupBy.getGroupingSets()) {
                if (o instanceof Expression) {
                    validateOptionalExpression((Expression) o);
                } else if (o instanceof ExpressionList) {
                    validateOptionalExpressions(((ExpressionList) o).getExpressions());
                }
            }
        }
    }

}
