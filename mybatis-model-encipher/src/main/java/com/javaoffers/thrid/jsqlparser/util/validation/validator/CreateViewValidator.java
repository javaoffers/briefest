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
import com.javaoffers.thrid.jsqlparser.statement.create.view.CreateView;
import com.javaoffers.thrid.jsqlparser.statement.create.view.ForceOption;
import com.javaoffers.thrid.jsqlparser.statement.create.view.TemporaryOption;
import com.javaoffers.thrid.jsqlparser.statement.select.Select;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationCapability;
import com.javaoffers.thrid.jsqlparser.util.validation.metadata.NamedObject;

/**
 * @author gitmotte
 */
public class CreateViewValidator extends AbstractValidator<CreateView> {

    @Override
    public void validate(CreateView createView) {
        for (ValidationCapability c : getCapabilities()) {
            validateFeature(c, Feature.createView);
            validateFeature(c, createView.isOrReplace(), Feature.createOrReplaceView);
            validateFeature(c, !ForceOption.NONE.equals(createView.getForce()), Feature.createViewForce);
            validateFeature(c, !TemporaryOption.NONE.equals(createView.getTemporary()), Feature.createViewTemporary);
            validateFeature(c, createView.isMaterialized(), Feature.createViewMaterialized);
            validateName(c, NamedObject.view, createView.getView().getFullyQualifiedName(), false);
        }
        SelectValidator v = getValidator(SelectValidator.class);
        Select select = createView.getSelect();
        if (isNotEmpty(select.getWithItemsList())) {
            select.getWithItemsList().forEach(wi -> wi.accept(v));
        }
        select.getSelectBody().accept(v);

    }

}
