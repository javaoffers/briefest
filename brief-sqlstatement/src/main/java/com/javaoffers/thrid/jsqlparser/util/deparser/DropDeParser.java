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

import com.javaoffers.thrid.jsqlparser.statement.drop.Drop;
import com.javaoffers.thrid.jsqlparser.statement.select.PlainSelect;

public class DropDeParser extends AbstractDeParser<Drop> {

    public DropDeParser(StringBuilder buffer) {
        super(buffer);
    }

    @Override
    public void deParse(Drop drop) {
        buffer.append("DROP ");
        buffer.append(drop.getType());
        if (drop.isIfExists()) {
            buffer.append(" IF EXISTS");
        }

        buffer.append(" ").append(drop.getName());

        if (drop.getParameters() != null && !drop.getParameters().isEmpty()) {
            buffer.append(" ").append(PlainSelect.getStringList(drop.getParameters()));
        }
    }

}
