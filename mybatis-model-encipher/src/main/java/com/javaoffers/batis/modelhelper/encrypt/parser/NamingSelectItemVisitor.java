package com.javaoffers.batis.modelhelper.encrypt.parser;

import com.javaoffers.thrid.jsqlparser.expression.Alias;
import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.Parenthesis;
import com.javaoffers.thrid.jsqlparser.schema.Column;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectExpressionItem;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectItemVisitorAdapter;

/**
 * @author mingJie
 */
public class NamingSelectItemVisitor extends SelectItemVisitorAdapter {

    private NamingSelectContent namingContent;

    @Override
    public void visit(SelectExpressionItem item) {

        Expression expression = item.getExpression();
        Column selectOnlyColumn = getSelectOnlyColumn(expression);
        String oldColumnName = null;
        if(selectOnlyColumn != null){
            oldColumnName = selectOnlyColumn.getColumnName();
        }
        expression.accept(new NamingSelectExpressionVisitorAdapter(ConditionName.SELECT_COL_NAME ,namingContent));
        String newColumnName = null;
        if(selectOnlyColumn != null){
            newColumnName = selectOnlyColumn.getColumnName();
        }

        //如果新的和旧的不一样,则说明被解密更改了(select 里面只有解密操作)
        if(oldColumnName != null && !oldColumnName.equalsIgnoreCase(newColumnName)){
            //将旧的column作为别名.
            setSelectColumnMakeAlias(oldColumnName, item);
        }
    }

    public void setSelectColumnMakeAlias(String aliasName, SelectExpressionItem item){
        if(item.getAlias() == null){
            //处理column被解密后名称不正确问题. 用原来的column做解密后的别名
            item.setAlias(new Alias(aliasName, true));
        }
    }

    public Column getSelectOnlyColumn(Expression expression ) {
        if (expression instanceof Column) {
            return (Column) expression;
        } else if (expression instanceof Parenthesis) {
            Parenthesis parenthesis = (Parenthesis) expression;
            Expression innerExpre = parenthesis.getExpression();
            if (innerExpre instanceof Column) {
                return (Column) innerExpre;
            }
        }
        return null;
    }

    public NamingSelectItemVisitor(NamingSelectContent namingContent) {
        this.namingContent = namingContent;
    }
}
