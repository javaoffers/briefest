package com.javaoffers.batis.modelhelper.encrypt.visitor;

import com.javaoffers.batis.modelhelper.encrypt.ConditionName;
import com.javaoffers.batis.modelhelper.encrypt.NamingContent;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;

/**
 * @author mingJie
 */
public class NamingSelectItemVisitor extends SelectItemVisitorAdapter {

    private NamingContent namingContent;

    @Override
    public void visit(SelectExpressionItem item) {
        item.getExpression().accept(new NamingExpressionVisitorAdapter(ConditionName.SELECT_COL_NAME ,namingContent));
    }

    public NamingSelectItemVisitor(NamingContent namingContent) {
        this.namingContent = namingContent;
    }
}
