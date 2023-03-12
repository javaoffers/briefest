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
import com.javaoffers.thrid.jsqlparser.statement.create.index.CreateIndex;
import com.javaoffers.thrid.jsqlparser.statement.create.table.Index;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationCapability;
import com.javaoffers.thrid.jsqlparser.util.validation.metadata.NamedObject;

/**
 * @author gitmotte
 */
public class CreateIndexValidator extends AbstractValidator<CreateIndex> {

    @Override
    public void validate(CreateIndex createIndex) {
        Index index = createIndex.getIndex();
        for (ValidationCapability c : getCapabilities()) {
            validateFeature(c, Feature.createIndex);
            validateName(c, NamedObject.table, createIndex.getTable().getFullyQualifiedName());
            validateName(c, NamedObject.index, index.getName(), false);
            validateOptionalColumnNames(c, index.getColumnsNames(), NamedObject.table);
        }
    }

}
