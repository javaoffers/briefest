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

import com.javaoffers.thrid.jsqlparser.JSQLParserException;
import com.javaoffers.thrid.jsqlparser.parser.CCJSqlParserUtil;
import com.javaoffers.thrid.jsqlparser.statement.Statements;

import java.util.function.Consumer;

/**
 * package - private class for {@link Validation} to parse the statements
 * within it's own {@link ValidationCapability}
 *
 * @author gitmotte
 */
final class ParseCapability implements ValidationCapability {

    public static final String NAME = "parsing";

    private String statements;
    private Statements parsedStatement;

    public ParseCapability(String statements) {
        this.statements = statements;
    }

    public String getStatements() {
        return statements;
    }

    /**
     * @return <code>null</code> on parse error, otherwise the {@link Statements}
     *         parsed.
     */
    public Statements getParsedStatements() {
        return parsedStatement;
    }

    @Override
    public void validate(ValidationContext context, Consumer<ValidationException> errorConsumer) {
        try {
            this.parsedStatement = CCJSqlParserUtil.parseStatements(
                    CCJSqlParserUtil.newParser(statements).withConfiguration(context.getConfiguration()));
        } catch (JSQLParserException e) {
            errorConsumer.accept(new ParseException("Cannot parse com.javaoffers.thrid.sqlparse.statement: " + e.getMessage(), e));
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

}
