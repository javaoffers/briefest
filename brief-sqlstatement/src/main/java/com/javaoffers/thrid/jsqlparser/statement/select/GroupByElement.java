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

import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.operators.relational.ExpressionList;

import java.util.*;

public class GroupByElement {
    // ExpressionList has 'usingBrackets = true' and so we need to switch it off explicitly
    private ExpressionList groupByExpressions = new ExpressionList().withUsingBrackets(false);
    private List groupingSets = new ArrayList();

    public boolean isUsingBrackets() {
        return groupByExpressions.isUsingBrackets();
    }

    public void setUsingBrackets(boolean usingBrackets) {
        this.groupByExpressions.setUsingBrackets(usingBrackets);
    }

    public GroupByElement withUsingBrackets(boolean usingBrackets) {
        this.groupByExpressions.setUsingBrackets(usingBrackets);
        return this;
    }

    public void accept(GroupByVisitor groupByVisitor) {
        groupByVisitor.visit(this);
    }
    
    public ExpressionList getGroupByExpressionList() {
        return groupByExpressions;
    }
    
    public void setGroupByExpressionList(ExpressionList groupByExpressions) {
        this.groupByExpressions=groupByExpressions;
    }
    
    @Deprecated
    public List<Expression> getGroupByExpressions() {
        return groupByExpressions.getExpressions();
    }

    @Deprecated
    public void setGroupByExpressions(List<Expression> groupByExpressions) {
        this.groupByExpressions.setExpressions(groupByExpressions);
    }

    @Deprecated
    public void addGroupByExpression(Expression groupByExpression) {
        if (groupByExpressions.getExpressions()==null) {
            groupByExpressions.setExpressions(new ArrayList());
        }
        groupByExpressions.getExpressions().add(groupByExpression);
    }

    public List getGroupingSets() {
        return groupingSets;
    }

    public void setGroupingSets(List groupingSets) {
        this.groupingSets = groupingSets;
    }

    public void addGroupingSet(Expression expr) {
        this.groupingSets.add(expr);
    }

    public void addGroupingSet(ExpressionList list) {
        this.groupingSets.add(list);
    }

    @Override
    @SuppressWarnings({"PMD.CyclomaticComplexity"})
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("GROUP BY ");

        if (groupByExpressions.getExpressions()!=null && groupByExpressions.getExpressions().size() > 0) {
            if (groupByExpressions.isUsingBrackets()) {
                b.append("( ");
            }
            b.append(PlainSelect.getStringList(groupByExpressions.getExpressions()));
            if (groupByExpressions.isUsingBrackets()) {
                b.append(" )");
            }
        } else if (groupingSets.size() > 0) {
            b.append("GROUPING SETS (");
            boolean first = true;
            for (Object o : groupingSets) {
                if (first) {
                    first = false;
                } else {
                    b.append(", ");
                }
                if (o instanceof Expression) {
                    b.append(o.toString());
                } else if (o instanceof ExpressionList) {
                    ExpressionList list = (ExpressionList) o;
                    b.append(list.getExpressions() == null ? "()" : list.toString());
                }
            }
            b.append(")");
        } else {
            if (groupByExpressions.isUsingBrackets()) {
                b.append("()");
            }
        }

        return b.toString();
    }

    public GroupByElement withGroupByExpressions(List<Expression> groupByExpressions) {
        this.setGroupByExpressions(groupByExpressions);
        return this;
    }

    public GroupByElement withGroupingSets(List groupingSets) {
        this.setGroupingSets(groupingSets);
        return this;
    }

    public GroupByElement addGroupByExpressions(Expression... groupByExpressions) {
        List<Expression> collection
                = Optional.ofNullable(getGroupByExpressions()).orElseGet(ArrayList::new);
        Collections.addAll(collection, groupByExpressions);
        return this.withGroupByExpressions(collection);
    }

    public GroupByElement addGroupByExpressions(Collection<? extends Expression> groupByExpressions) {
        List<Expression> collection
                = Optional.ofNullable(getGroupByExpressions()).orElseGet(ArrayList::new);
        collection.addAll(groupByExpressions);
        return this.withGroupByExpressions(collection);
    }

    public GroupByElement addGroupingSets(Object... groupingSets) {
        List collection = Optional.ofNullable(getGroupingSets()).orElseGet(ArrayList::new);
        Collections.addAll(collection, groupingSets);
        return this.withGroupingSets(collection);
    }

    public GroupByElement addGroupingSets(Collection<? extends Object> groupingSets) {
        List collection = Optional.ofNullable(getGroupingSets()).orElseGet(ArrayList::new);
        collection.addAll(groupingSets);
        return this.withGroupingSets(collection);
    }
}
