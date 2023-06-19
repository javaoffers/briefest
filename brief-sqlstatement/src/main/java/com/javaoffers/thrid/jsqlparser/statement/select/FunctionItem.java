/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement.select;

import com.javaoffers.thrid.jsqlparser.Model;
import com.javaoffers.thrid.jsqlparser.expression.Alias;
import com.javaoffers.thrid.jsqlparser.expression.Function;

public class FunctionItem implements Model {

    private Function function;
    private Alias alias;

    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return function + ((alias != null) ? alias.toString() : "");
    }

    public FunctionItem withFunction(Function function) {
        this.setFunction(function);
        return this;
    }

    public FunctionItem withAlias(Alias alias) {
        this.setAlias(alias);
        return this;
    }
}
