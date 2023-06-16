/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.deparser;

/**
 * A base for a Statement DeParser
 *
 * @param <S> the type of com.javaoffers.thrid.sqlparse.statement this DeParser supports
 */
abstract class AbstractDeParser<S> {
    protected StringBuilder buffer;

    protected AbstractDeParser(StringBuilder buffer) {
        this.buffer = buffer;
    }

    public StringBuilder getBuffer() {
        return buffer;
    }

    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }

    /**
     * DeParses the given com.javaoffers.thrid.sqlparse.statement into the buffer
     *
     * @param statement the com.javaoffers.thrid.sqlparse.statement to deparse
     */
    abstract void deParse(S statement);
}
