/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement.create.view;

import com.javaoffers.thrid.jsqlparser.schema.Table;
import com.javaoffers.thrid.jsqlparser.statement.Statement;
import com.javaoffers.thrid.jsqlparser.statement.StatementVisitor;
import com.javaoffers.thrid.jsqlparser.statement.select.PlainSelect;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectBody;

import java.util.*;

public class AlterView implements Statement {

    private Table view;
    private SelectBody selectBody;
    private boolean useReplace = false;
    private List<String> columnNames = null;

    @Override
    public void accept(StatementVisitor statementVisitor) {
        statementVisitor.visit(this);
    }

    public Table getView() {
        return view;
    }

    public void setView(Table view) {
        this.view = view;
    }

    public SelectBody getSelectBody() {
        return selectBody;
    }

    public void setSelectBody(SelectBody selectBody) {
        this.selectBody = selectBody;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public boolean isUseReplace() {
        return useReplace;
    }

    public void setUseReplace(boolean useReplace) {
        this.useReplace = useReplace;
    }

    @Override
    public String toString() {
        StringBuilder sql;
        if (useReplace) {
            sql = new StringBuilder("REPLACE ");
        } else {
            sql = new StringBuilder("ALTER ");
        }
        sql.append("VIEW ");
        sql.append(view);
        if (columnNames != null) {
            sql.append(PlainSelect.getStringList(columnNames, true, true));
        }
        sql.append(" AS ").append(selectBody);
        return sql.toString();
    }

    public AlterView withView(Table view) {
        this.setView(view);
        return this;
    }

    public AlterView withSelectBody(SelectBody selectBody) {
        this.setSelectBody(selectBody);
        return this;
    }

    public AlterView withUseReplace(boolean useReplace) {
        this.setUseReplace(useReplace);
        return this;
    }

    public AlterView withColumnNames(List<String> columnNames) {
        this.setColumnNames(columnNames);
        return this;
    }

    public AlterView addColumnNames(String... columnNames) {
        List<String> collection = Optional.ofNullable(getColumnNames()).orElseGet(ArrayList::new);
        Collections.addAll(collection, columnNames);
        return this.withColumnNames(collection);
    }

    public AlterView addColumnNames(Collection<String> columnNames) {
        List<String> collection = Optional.ofNullable(getColumnNames()).orElseGet(ArrayList::new);
        collection.addAll(columnNames);
        return this.withColumnNames(collection);
    }

    public <E extends SelectBody> E getSelectBody(Class<E> type) {
        return type.cast(getSelectBody());
    }
}
