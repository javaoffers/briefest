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

import com.javaoffers.thrid.jsqlparser.util.validation.ContextKey;

public enum AllowedTypesContext implements ContextKey {
    /**
     * a collection of allowed {@link Class}es
     */
    allowed_types,
    /**
     * the object given (may be null)
     */
    argument,
    /**
     * a boolean, default = true
     */
    allow_null
}
