/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement.create.procedure;

import com.javaoffers.thrid.jsqlparser.statement.CreateFunctionalStatement;

import java.util.Collection;
import java.util.List;

/**
 * A {@code CREATE PROCEDURE} com.javaoffers.thrid.sqlparse.statement
 */
public class CreateProcedure extends CreateFunctionalStatement {

    public CreateProcedure() {
        super("PROCEDURE");
    }

    public CreateProcedure(List<String> functionDeclarationParts) {
        this(false, functionDeclarationParts);
    }

    public CreateProcedure(boolean orReplace, List<String> functionDeclarationParts) {
        super(orReplace, "PROCEDURE", functionDeclarationParts);
    }

    @Override
    public CreateProcedure withFunctionDeclarationParts(List<String> functionDeclarationParts) {
        return (CreateProcedure) super.withFunctionDeclarationParts(functionDeclarationParts);
    }

    @Override
    public CreateProcedure addFunctionDeclarationParts(String... functionDeclarationParts) {
        return (CreateProcedure) super.addFunctionDeclarationParts(functionDeclarationParts);
    }

    @Override
    public CreateProcedure addFunctionDeclarationParts(Collection<String> functionDeclarationParts) {
        return (CreateProcedure) super.addFunctionDeclarationParts(functionDeclarationParts);
    }

}
