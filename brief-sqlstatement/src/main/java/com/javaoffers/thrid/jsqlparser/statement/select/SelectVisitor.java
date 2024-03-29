/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement.select;

import com.javaoffers.thrid.jsqlparser.statement.values.ValuesStatement;

public interface SelectVisitor {

    void visit(PlainSelect plainSelect);

    void visit(SetOperationList setOpList);

    void visit(WithItem withItem);

    void visit(ValuesStatement aThis);
}
