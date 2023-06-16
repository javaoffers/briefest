package com.javaoffers.brief.modelhelper.encrypt.parser.insert;

import com.javaoffers.brief.modelhelper.encrypt.parser.NamingSelectContent;
import com.javaoffers.thrid.jsqlparser.expression.operators.relational.ExpressionList;
import com.javaoffers.thrid.jsqlparser.expression.operators.relational.ItemsListVisitorAdapter;

/**
 * @author cmj
 * @createTime 2023年03月05日 18:06:00
 */
public class NamingInsertItemsListVisitorAdapter extends ItemsListVisitorAdapter {
    private NamingSelectContent namingContent;

    public NamingInsertItemsListVisitorAdapter(NamingSelectContent namingContent) {
        this.namingContent = namingContent;
    }

    @Override
    public void visit(ExpressionList expressionList) {
        expressionList.getExpressions().forEach(expression -> {
            NamingInsertExpressionVisitorAdapter namingInsertExpressionVisitorAdapter =
                    new NamingInsertExpressionVisitorAdapter(this.namingContent);
            //支持 insert table (xx, xx) values ( (subSelect), xx ). #values 中含有子查询的
            namingInsertExpressionVisitorAdapter.setSelectVisitor(new NamingInsertVisitorAdapter(this.namingContent));
            expression.accept(namingInsertExpressionVisitorAdapter);
            int needEncryptColMarkIndex = this.namingContent.getNeedEncryptColMarkIndex();
            this.namingContent.setNeedEncryptColMarkIndex(needEncryptColMarkIndex + 1);

        });
    }

}
