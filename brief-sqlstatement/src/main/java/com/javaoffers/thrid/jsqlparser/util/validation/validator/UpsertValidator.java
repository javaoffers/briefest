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

import com.javaoffers.thrid.jsqlparser.parser.feature.Feature;
import com.javaoffers.thrid.jsqlparser.statement.select.Select;
import com.javaoffers.thrid.jsqlparser.statement.upsert.Upsert;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationCapability;

/**
 * @author gitmotte
 */
public class UpsertValidator extends AbstractValidator<Upsert> {

    @Override
    public void validate(Upsert upsert) {
        for (ValidationCapability c : getCapabilities()) {
            validateFeature(c, Feature.upsert);
        }
        validateOptionalFromItem(upsert.getTable());
        validateOptionalExpressions(upsert.getColumns());
        validateOptionalItemsList(upsert.getItemsList());
        validateOptionalSelect(upsert.getSelect());
        if (upsert.isUseDuplicate()) {
            validateDuplicate(upsert);
        }
    }

    private void validateOptionalSelect(Select select) {
        if (select != null) {
            SelectValidator v = getValidator(SelectValidator.class);
            if (isNotEmpty(select.getWithItemsList())) {
                select.getWithItemsList().forEach(with -> with.accept(v));
            }
            select.getSelectBody().accept(v);
        }
    }

    private void validateDuplicate(Upsert upsert) {
        validateOptionalExpressions(upsert.getDuplicateUpdateColumns());
        validateOptionalExpressions(upsert.getDuplicateUpdateExpressionList());
    }

}
