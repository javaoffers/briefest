/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.validation.allowedtypes;

import com.javaoffers.thrid.jsqlparser.util.validation.ValidationCapability;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationContext;
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationException;

import java.util.Collection;
import java.util.function.Consumer;

public class AllowedTypesValidation implements ValidationCapability {

    public static final String NAME = "allowed types";

    @Override
    public void validate(ValidationContext context, Consumer<ValidationException> errorConsumer) {
        Object arg = context.getOptional(AllowedTypesContext.argument, Object.class);
        Boolean allowNull = context.getOptional(AllowedTypesContext.allow_null, Boolean.class);
        @SuppressWarnings("unchecked")
        Collection<Class<?>> allowedTypes = context.get(AllowedTypesContext.allowed_types, Collection.class);
        if (arg != null) {
            boolean error = true;
            for (Class<?> cls : allowedTypes) {
                if (cls.isAssignableFrom(arg.getClass())) {
                    error = false;
                    break;
                }
            }
            if (error) {
                errorConsumer.accept(toError(arg.getClass() + " is not a valid argument - expected one of " + allowedTypes));
            }
        } else if (Boolean.FALSE.equals(allowNull)) {
            errorConsumer.accept(toError("argument is missing one of " + allowedTypes));
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

}
