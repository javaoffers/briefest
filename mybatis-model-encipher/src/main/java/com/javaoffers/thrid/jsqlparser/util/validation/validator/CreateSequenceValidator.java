/*
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.validation.validator;

import com.javaoffers.thrid.jsqlparser.parser.feature.Feature;
import com.javaoffers.thrid.jsqlparser.statement.create.sequence.CreateSequence;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationCapability;
import com.javaoffers.thrid.jsqlparser.util.validation.metadata.NamedObject;

/**
 * @author gitmotte
 */
public class CreateSequenceValidator extends AbstractValidator<CreateSequence> {


    @Override
    public void validate(CreateSequence statement) {
        for (ValidationCapability c : getCapabilities()) {
            validateFeature(Feature.createSequence);
            validateName(c, NamedObject.sequence, statement.getSequence().getFullyQualifiedName(), false);
        }
    }
}
