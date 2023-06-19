/*
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.deparser;

import com.javaoffers.thrid.jsqlparser.statement.create.sequence.CreateSequence;

/**
 * A class to de-parse (that is, transform from JSqlParser hierarchy into a string) a
 * {@link CreateSequence}
 */
public class CreateSequenceDeParser extends AbstractDeParser<CreateSequence>{

    /**
     * @param buffer the buffer that will be filled with the CreatSequence
     */
    public CreateSequenceDeParser(StringBuilder buffer) {
        super(buffer);
    }

    @Override
    public void deParse(CreateSequence statement) {
        buffer.append("CREATE SEQUENCE ");
        buffer.append(statement.getSequence());
    }
}
