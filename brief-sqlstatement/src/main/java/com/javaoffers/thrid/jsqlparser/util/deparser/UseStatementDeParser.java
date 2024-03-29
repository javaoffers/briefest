/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.deparser;

import com.javaoffers.thrid.jsqlparser.statement.UseStatement;

public class UseStatementDeParser extends AbstractDeParser<UseStatement> {

    public UseStatementDeParser(StringBuilder buffer) {
        super(buffer);
    }

    @Override
    public void deParse(UseStatement set) {
        buffer.append("USE ");
        if (set.hasSchemaKeyword()) {
            buffer.append("SCHEMA ");
        }
        buffer.append(set.getName());
    }
}
