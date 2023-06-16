/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.validation;

import com.javaoffers.thrid.jsqlparser.expression.Alias;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValidationUtil {

    private ValidationUtil() {
        // no construction allowed
    }

    public static <T> List<String> map(List<T> list, Function<? super T, String> fn) {
        return list.stream().map(fn).collect(Collectors.toList());
    }

    public static String getAlias(Alias alias) {
        return Optional.ofNullable(alias).map(Alias::getName).orElse(null);
    }

}
