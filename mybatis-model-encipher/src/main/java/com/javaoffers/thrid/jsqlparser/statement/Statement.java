/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement;

import com.javaoffers.thrid.jsqlparser.*;

public interface Statement extends Model {
    void accept(StatementVisitor statementVisitor);
}
