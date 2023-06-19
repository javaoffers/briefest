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

import com.javaoffers.thrid.jsqlparser.parser.feature.FeatureConfiguration;
import com.javaoffers.thrid.jsqlparser.statement.Statement;
import com.javaoffers.thrid.jsqlparser.statement.Statements;
import com.javaoffers.thrid.jsqlparser.util.validation.validator.StatementValidator;

import java.util.*;
import java.util.Map.Entry;

/**
 * Parses the given com.javaoffers.thrid.sqlparse.statement list with {@link ParseCapability} and performs
 * validation with configured {@link ValidationCapability}'s.
 * 
 * Errors are are reported by calling {@link #validate()}.
 * 
 * @author gitmotte
 * @see #getErrors()
 * @see #validate()
 */
public class Validation {

    private FeatureConfiguration featureConfiguration;
    private Collection<? extends ValidationCapability> capabilities;
    private List<String> statementsList;

    private List<ValidationError> errors;
    private Statements parsedStatements;

    public Validation(Collection<? extends ValidationCapability> capabilities, String... statements) {
        this(new FeatureConfiguration(), capabilities, statements);
    }

    public Validation(FeatureConfiguration featureConfiguration,
            Collection<? extends ValidationCapability> capabilities, String... statements) {
        this.featureConfiguration = featureConfiguration;
        this.capabilities = capabilities;
        this.statementsList = Arrays.asList(statements);
    }

    /**
     * @return the errors - may be an empty list.
     */
    public List<ValidationError> validate() {
        this.errors = new ArrayList<>();

        ValidationContext context = createValidationContext(featureConfiguration, capabilities);
        for (String statements : statementsList) {

            ParseCapability parse = new ParseCapability(statements);
            parse.validate(context, e -> errors.add(new ValidationError(statements).withCapability(parse).addError(e)));

            parsedStatements = parse.getParsedStatements();
            if (parsedStatements != null && parsedStatements.getStatements() != null && !capabilities.isEmpty() ) {
                for (Statement parsedStatement : parsedStatements.getStatements()) {
                    Map<ValidationCapability, Set<ValidationException>> errorMap = validate(parsedStatement, context);
                    errors.addAll(toValidationErrors(statements, parsedStatement, errorMap));
                }
            }

        }
        return errors;
    }

    public FeatureConfiguration getFeatureConfiguration() {
        return featureConfiguration;
    }

    public Collection<? extends ValidationCapability> getCapabilities() {
        return capabilities;
    }

    public List<String> getStatements() {
        return statementsList;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public Statements getParsedStatements() {
        return parsedStatements;
    }

    // STATIC com.javaoffers.thrid.sqlparse.util-methods

    /**
     * @param capabilities
     * @param statements
     * @return a list of {@link ValidationError}'s
     */
    public static List<ValidationError> validate(Collection<? extends ValidationCapability> capabilities,
            String... statements) {
        return new Validation(capabilities, statements).validate();
    }

    /**
     * @param config
     * @param capabilities
     * @return a {@link ValidationContext} of the given config and capabilities
     */
    public static ValidationContext createValidationContext(FeatureConfiguration config,
            Collection<? extends ValidationCapability> capabilities) {
        ValidationContext context = new ValidationContext();
        context.setCapabilities(new ArrayList<>(capabilities));
        context.setConfiguration(config);
        return context;
    }

    /**
     * @param statements
     * @param parsedStatement
     * @param errorMap
     * @return a list of {@link ValidationError}'
     */
    public static List<ValidationError> toValidationErrors(String statements,
            Statement parsedStatement, Map<ValidationCapability, Set<ValidationException>> errorMap) {
        List<ValidationError> errors = new ArrayList<>();
        for (Entry<ValidationCapability, Set<ValidationException>> e : errorMap.entrySet()) {
            errors.add(new ValidationError(statements).withParsedStatement(parsedStatement)
                    .withCapability(e.getKey()).addErrors(e.getValue()));
        }
        return errors;
    }

    /**
     * @param statement
     * @param context
     * @return a map mapping the {@link ValidationCapability} to a set of
     *         {@link ValidationException}s
     */
    public static Map<ValidationCapability, Set<ValidationException>> validate(Statement statement,
            ValidationContext context) {
        StatementValidator validator = new StatementValidator();
        validator.setContext(context);
        validator.validate(statement);
        return validator.getValidationErrors();
    }

}
