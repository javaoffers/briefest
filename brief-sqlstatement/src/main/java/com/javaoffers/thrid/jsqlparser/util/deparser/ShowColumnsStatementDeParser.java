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

import com.javaoffers.thrid.jsqlparser.statement.ShowColumnsStatement;

public class ShowColumnsStatementDeParser extends AbstractDeParser<ShowColumnsStatement> {

    public ShowColumnsStatementDeParser(StringBuilder buffer) {
        super(buffer);
    }

    @Override
    public void deParse(ShowColumnsStatement show) {
        buffer.append("SHOW COLUMNS FROM ").append(show.getTableName());
    }
}
