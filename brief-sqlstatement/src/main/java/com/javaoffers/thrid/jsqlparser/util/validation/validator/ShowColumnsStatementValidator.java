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
import com.javaoffers.thrid.jsqlparser.statement.ShowColumnsStatement;
import com.javaoffers.thrid.jsqlparser.util.validation.metadata.NamedObject;

/**
 * @author gitmotte
 */
public class ShowColumnsStatementValidator extends AbstractValidator<ShowColumnsStatement> {

    @Override
    public void validate(ShowColumnsStatement show) {
        validateFeatureAndName(Feature.showColumns, NamedObject.table, show.getTableName());
    }
}
