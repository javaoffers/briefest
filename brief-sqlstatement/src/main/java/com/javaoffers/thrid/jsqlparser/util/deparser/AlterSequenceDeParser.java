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

import com.javaoffers.thrid.jsqlparser.statement.alter.sequence.AlterSequence;

/**
 * A class to de-parse (that is, transform from JSqlParser hierarchy into a string) a
 * {@link AlterSequence}
 */
public class AlterSequenceDeParser extends AbstractDeParser<AlterSequence> {

    /**
     * @param buffer the buffer that will be filled with the AlterSequence
     */
    public AlterSequenceDeParser(StringBuilder buffer) {
        super(buffer);
    }

    @Override
    public void deParse(AlterSequence statement) {
        buffer.append("ALTER SEQUENCE ");
        buffer.append(statement.getSequence());
    }
}
