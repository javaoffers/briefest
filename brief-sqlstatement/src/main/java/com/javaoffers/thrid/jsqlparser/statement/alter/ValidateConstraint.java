/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement.alter;

public class ValidateConstraint implements ConstraintState {

    private boolean not;

    public ValidateConstraint() {
        // empty constructor
    }

    public ValidateConstraint(boolean not) {
        this.not = not;
    }

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    @Override
    public String toString() {
        return not ? "NOVALIDATE" : "VALIDATE";
    }

    public ValidateConstraint withNot(boolean not) {
        this.setNot(not);
        return this;
    }
}
