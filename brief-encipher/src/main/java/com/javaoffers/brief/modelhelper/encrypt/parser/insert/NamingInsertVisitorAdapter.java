package com.javaoffers.brief.modelhelper.encrypt.parser.insert;

import com.javaoffers.brief.modelhelper.encrypt.parser.ConditionName;
import com.javaoffers.brief.modelhelper.encrypt.parser.NamingFromItemVisitorAdapter;
import com.javaoffers.brief.modelhelper.encrypt.parser.NamingSelectContent;
import com.javaoffers.brief.modelhelper.encrypt.parser.NamingSelectExpressionVisitorAdapter;
import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.statement.select.*;
import org.apache.commons.collections4.CollectionUtils;


import java.util.Collection;
import java.util.List;

/**
 * @author mingJie
 */
public class NamingInsertVisitorAdapter extends SelectVisitorAdapter {

    private NamingSelectContent namingContent;

    public NamingInsertVisitorAdapter(NamingSelectContent namingContent) {
        this.namingContent = namingContent;
    }

    // 针对与 insert into table select xx. 查询的内通不做解密, 只解密where中的数据.
    // 这种语句看作,从原始表导入到另外一张表, 加密的数据不变.
    @Override
    public void visit(PlainSelect plainSelect) {
        //step1: 解析 from table信息.
        FromItem fromItem = plainSelect.getFromItem();
        this.namingContent.setHavaJoin(CollectionUtils.isNotEmpty(plainSelect.getJoins()));
        fromItem.accept(new NamingFromItemVisitorAdapter(this.namingContent));

        //step2: 解析 join table on (这样可以先拿到joinTable的信息)
        List<Join> joins = plainSelect.getJoins();
        if(CollectionUtils.isNotEmpty(joins)){
            joins.forEach(join -> {
                //join table
                FromItem rightItem = join.getRightItem();
                rightItem.accept(new NamingFromItemVisitorAdapter(this.namingContent));
                Collection<Expression> onExpressions = join.getOnExpressions();
                //on
                if(CollectionUtils.isNotEmpty(onExpressions)){
                    onExpressions.forEach(onExpression->{
                        onExpression.accept(new NamingInsertSelectExpressionVisitorAdapter(ConditionName.ON, this.namingContent));
                    });
                }
            });
        }

        //step3: 在 insert into select colName xx 这种情况不解析select 中的colname (colname若为子查询则除外,
        // 而且这种情况还要进一步解析). 保持加密.
        //因为这种sql, 理解为导入数据. 导入到另一张表中的数据应该也要保持加密的状态.
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        if(CollectionUtils.isNotEmpty(selectItems)){
            selectItems.forEach(selectItem -> {
                //colname 可能存在子查询语句如下: (因此需要继续解析, 深度的子查询where也要被解析出来).
                //insert into select colname , (select colname from xx where xx ) , xx from tableName
                //解析深度的子查询语句.
                selectItem.accept(new NamingInsertSelectItemVisitorAdapter(namingContent));
            });
        }

        //step4: 解析 where
        Expression where = plainSelect.getWhere();
        if(where != null){
            where.accept(new NamingSelectExpressionVisitorAdapter(ConditionName.WHERE, this.namingContent));
        }
    }

}
