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

import com.javaoffers.thrid.jsqlparser.statement.select.SetOperationList.SetOperationType;

public class ExceptOp extends SetOperation {

    public ExceptOp() {
        super(SetOperationType.EXCEPT);
    }
}
