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

import java.util.List;

/**
 * This helper class is mainly used for handling the CNF conversion.
 *
 * @author messfish
 *
 */
public final class MultiAndExpression extends MultipleExpression {

    public MultiAndExpression(List<Expression> childlist) {
        super(childlist);
    }

    @Override
    public String getStringExpression() {
        return "AND";
    }

}
