/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.validation.feature;

import com.javaoffers.thrid.jsqlparser.parser.feature.Feature;
import com.javaoffers.thrid.jsqlparser.parser.feature.FeatureSet;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationCapability;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationContext;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationException;

import java.util.Set;
import java.util.function.Consumer;

public interface FeatureSetValidation extends ValidationCapability, FeatureSet {

    String DEFAULT_NAME = "feature set";

    @Override
    default void validate(ValidationContext context, Consumer<ValidationException> errorConsumer) {
        Feature feature = context.get(FeatureContext.feature, Feature.class);
        if (!contains(feature)) {
            errorConsumer.accept(getMessage(feature));
        }
    }

    /**
     * @return all supported {@link Feature}'s
     */
    @Override
    Set<Feature> getFeatures();

    /**
     * @return the default message if not contained in the feature set
     */
    ValidationException getMessage(Feature feature);

    @Override
    default String getName() {
        return DEFAULT_NAME;
    }

}
