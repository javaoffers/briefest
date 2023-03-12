/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement.create.function;

import com.javaoffers.thrid.jsqlparser.statement.CreateFunctionalStatement;

import java.util.Collection;
import java.util.List;

/**
 * A {@code CREATE PROCEDURE} com.javaoffers.thrid.sqlparse.statement
 */
public class CreateFunction extends CreateFunctionalStatement {

    public CreateFunction() {
        super("FUNCTION");
    }

    public CreateFunction(List<String> functionDeclarationParts) {
        this(false, functionDeclarationParts);
    }
    
    public CreateFunction(boolean orReplace, List<String> functionDeclarationParts) {
        super(orReplace, "FUNCTION", functionDeclarationParts);
    }

    @Override
    public CreateFunction withFunctionDeclarationParts(List<String> functionDeclarationParts) {
        return (CreateFunction) super.withFunctionDeclarationParts(functionDeclarationParts);
    }

    @Override
    public CreateFunction addFunctionDeclarationParts(String... functionDeclarationParts) {
        return (CreateFunction) super.addFunctionDeclarationParts(functionDeclarationParts);
    }

    @Override
    public CreateFunction addFunctionDeclarationParts(Collection<String> functionDeclarationParts) {
        return (CreateFunction) super.addFunctionDeclarationParts(functionDeclarationParts);
    }

}
