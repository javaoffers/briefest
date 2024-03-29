/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.parser;

import com.javaoffers.thrid.jsqlparser.parser.feature.Feature;
import com.javaoffers.thrid.jsqlparser.parser.feature.FeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJSqlParser<P> {

    protected int jdbcParameterIndex = 0;
    protected boolean errorRecovery = false;
    protected List<ParseException> parseErrors = new ArrayList<>();

    public P withSquareBracketQuotation(boolean allowSquareBracketQuotation) {
        return withFeature(Feature.allowSquareBracketQuotation, allowSquareBracketQuotation);
    }

    public P withAllowComplexParsing(boolean allowComplexParsing) {
      return withFeature(Feature.allowComplexParsing, allowComplexParsing);
    }
    public P withFeature(Feature f, boolean enabled) {
        getConfiguration().setValue(f, enabled);
        return me();
    }

    public abstract FeatureConfiguration getConfiguration();

    public abstract P me();

    public boolean getAsBoolean(Feature f) {
        return getConfiguration().getAsBoolean(f);
    }

    public void setErrorRecovery(boolean errorRecovery) {
        this.errorRecovery = errorRecovery;
    }

    public List<ParseException> getParseErrors() {
        return parseErrors;
    }

}
