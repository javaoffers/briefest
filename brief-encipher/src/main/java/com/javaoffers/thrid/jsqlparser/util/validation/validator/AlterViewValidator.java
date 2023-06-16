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
import com.javaoffers.thrid.jsqlparser.statement.create.view.AlterView;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationCapability;
import com.javaoffers.thrid.jsqlparser.util.validation.metadata.NamedObject;

/**
 * @author gitmotte
 */
public class AlterViewValidator extends AbstractValidator<AlterView> {

    @Override
    public void validate(AlterView alterView) {
        for (ValidationCapability c : getCapabilities()) {
            validateFeature(Feature.alterView);
            validateFeature(c, alterView.isUseReplace(), Feature.alterViewReplace);
            validateName(c, NamedObject.view, alterView.getView().getFullyQualifiedName());
            validateOptionalColumnNames(c, alterView.getColumnNames());
        }
        alterView.getSelectBody().accept(getValidator(SelectValidator.class));
    }

}
