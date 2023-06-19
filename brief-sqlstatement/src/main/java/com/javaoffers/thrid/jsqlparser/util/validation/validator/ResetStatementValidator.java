/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2021 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.validation.validator;

import com.javaoffers.thrid.jsqlparser.parser.feature.Feature;
import com.javaoffers.thrid.jsqlparser.statement.ResetStatement;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationCapability;

public class ResetStatementValidator extends AbstractValidator<ResetStatement> {

    @Override
    public void validate(ResetStatement reset) {
        for (ValidationCapability c : getCapabilities()) {
            validateFeature(c, Feature.reset);
        }
    }
}
