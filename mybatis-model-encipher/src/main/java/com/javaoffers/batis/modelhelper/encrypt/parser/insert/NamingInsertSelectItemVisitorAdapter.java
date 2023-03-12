package com.javaoffers.batis.modelhelper.encrypt.parser.insert;

import com.javaoffers.batis.modelhelper.encrypt.parser.ConditionName;
import com.javaoffers.batis.modelhelper.encrypt.parser.NamingSelectContent;
import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectExpressionItem;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectItemVisitorAdapter;

/**
 * 用于解析 insert 中的子查询语句的 select column 部分.
 * @author mingJie
 */
public class NamingInsertSelectItemVisitorAdapter extends SelectItemVisitorAdapter  {

    private NamingSelectContent namingContent;

    @Override
    public void visit(SelectExpressionItem item) {

        Expression expression = item.getExpression();
        if(expression != null){
            expression.accept(new NamingInsertSelectExpressionVisitorAdapter(ConditionName.SELECT_COL_NAME ,namingContent));
        }
    }

    public NamingInsertSelectItemVisitorAdapter(NamingSelectContent namingContent) {
        this.namingContent = namingContent;
    }
}
