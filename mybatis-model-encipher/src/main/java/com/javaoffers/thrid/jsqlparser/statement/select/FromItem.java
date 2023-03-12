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

import com.javaoffers.thrid.jsqlparser.Model;
import com.javaoffers.thrid.jsqlparser.expression.Alias;

public interface FromItem extends Model {

    void accept(FromItemVisitor fromItemVisitor);

    Alias getAlias();

    default FromItem withAlias(Alias alias) {
        setAlias(alias);
        return this;
    }

    void setAlias(Alias alias);

    Pivot getPivot();

    default FromItem withPivot(Pivot pivot) {
        setPivot(pivot);
        return this;
    }

    void setPivot(Pivot pivot);

    UnPivot getUnPivot();

    default FromItem withUnPivot(UnPivot unpivot) {
        setUnPivot(unpivot);
        return this;
    }

    void setUnPivot(UnPivot unpivot);

}
