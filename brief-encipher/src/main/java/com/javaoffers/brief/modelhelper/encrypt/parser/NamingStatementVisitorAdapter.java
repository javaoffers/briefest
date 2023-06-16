package com.javaoffers.brief.modelhelper.encrypt.parser;

import com.javaoffers.brief.modelhelper.encrypt.parser.delete.NamingDeleteExpressionVisitorAdapter;
import com.javaoffers.brief.modelhelper.encrypt.parser.insert.NamingInsertItemsListVisitorAdapter;
import com.javaoffers.brief.modelhelper.encrypt.parser.insert.NamingInsertVisitorAdapter;
import com.javaoffers.brief.modelhelper.encrypt.parser.update.NamingUpdateExpressionVisitorAdapter;
import com.javaoffers.thrid.jsqlparser.expression.Expression;
import com.javaoffers.thrid.jsqlparser.expression.operators.relational.ItemsList;
import com.javaoffers.thrid.jsqlparser.schema.Column;
import com.javaoffers.thrid.jsqlparser.statement.StatementVisitorAdapter;
import com.javaoffers.thrid.jsqlparser.statement.delete.Delete;
import com.javaoffers.thrid.jsqlparser.statement.insert.Insert;
import com.javaoffers.thrid.jsqlparser.statement.select.Select;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectBody;
import com.javaoffers.thrid.jsqlparser.statement.update.Update;
import com.javaoffers.thrid.jsqlparser.statement.update.UpdateSet;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author mingJie
 */
public class NamingStatementVisitorAdapter extends StatementVisitorAdapter {

    private NamingSelectContent namingContent;

    public NamingStatementVisitorAdapter(NamingSelectContent namingContent) {
        this.namingContent = namingContent;
    }

    /**
     * 处理select 语句
     * @param select
     */
    @Override
    public void visit(Select select) {
        SelectBody selectBody = select.getSelectBody();
        selectBody.accept(new NamingSelectVisitorAdapter(this.namingContent));
    }

    /**
     * 处理update 语句.
     * set & where 这两部分都可以访问 update table 中的字段.
     * @param update
     */
    @Override
    public void visit(Update update) {

        //获取tableName
        String tableName = update.getTable().getName();
        this.namingContent.setUpdateTableName( tableName );
        //为了支持 update中出现的子查询. 可以访问update table 表中的字段
        this.namingContent.putAliasNameAndRealTableName(tableName, tableName);
        Expression where = update.getWhere();
        if(where != null){
            where.accept(new NamingUpdateExpressionVisitorAdapter(ConditionName.WHERE, namingContent));
        }
        ArrayList<UpdateSet> updateSets = update.getUpdateSets();
        if(CollectionUtils.isNotEmpty(updateSets)){
            updateSets.forEach(updateSet -> {
                ArrayList<Column> columns = updateSet.getColumns();
                ArrayList<Expression> expressions = updateSet.getExpressions();
                Assert.isTrue(expressions.size() > 0 ," expression is null");
                Column column = columns.get(0);
                String columnName = column.getColumnName();
                if(this.namingContent.isProcessorCloName(tableName, columnName)){
                    expressions.get(0).accept(new NamingUpdateExpressionVisitorAdapter(ConditionName.UPDATE_SET, namingContent));
                }
            });
        }
    }

    /**
     * 处理insert 语句.
     * 注意: insert 语句中的子查询 不能访问insert table 中的字段作为where 条件.
     * 所以 insert into table select xx  这里的select xx 不会被标记为子查询状态.
     * @param insert
     */
    @Override
    public void visit(Insert insert) {
        String tableName = insert.getTable().getName();
        this.namingContent.setInsertTableName(tableName);
        List<Column> columns = insert.getColumns();
        if(CollectionUtils.isEmpty(columns)){
            processValuesOrSelect(insert);
            return;
        }
        this.namingContent.setInsertColLen(columns.size());
        Pair<Set<String>, Consumer<ColNameProcessorInfo>> pair = this.namingContent.getProcessor().get(tableName);
        if(pair == null){
            //insert into tableA xxx select xx from tableB(这个table可能需要解密处理).
            processValuesOrSelect(insert);
            return;
        }
        Set<String> colNames = pair.getLeft();
        boolean[] needEncryptColMark = new boolean[columns.size()];
        for(int i=0; i < columns.size(); i++){
            if(colNames.contains(columns.get(i).getColumnName())){
                needEncryptColMark[i] = true;
            }
        }
        this.namingContent.setNeedEncryptColMark(needEncryptColMark);

        processValuesOrSelect(insert);
    }

    private void processValuesOrSelect(Insert insert) {
        //values (xx, xx)
        ItemsList itemsList = insert.getItemsList();
        if(itemsList != null){
            itemsList.accept(new NamingInsertItemsListVisitorAdapter(this.namingContent));
        }
        //insert table (xx) select xx
        Select select = insert.getSelect();//这里指insert  select xxx
        insertSelect(select);
    }

    private void insertSelect(Select select) {
        if(select == null){ return;}
        select.accept(new NamingStatementVisitorAdapter(this.namingContent){
            @Override
            public void visit(Select select) {
                SelectBody selectBody = select.getSelectBody();
                selectBody.accept(new NamingInsertVisitorAdapter(namingContent));
            }
        });
    }

    /**
     * 处理delete语句
     * @param delete
     */
    @Override
    public void visit(Delete delete) {
        String tableName = delete.getTable().getName();
        this.namingContent.setDeleteTableName(tableName);
        //为了支持 delete中出现的子查询去访问 delete表中的字段属性.
        this.namingContent.putAliasNameAndRealTableName(tableName, tableName);
        Expression where = delete.getWhere();
        if(where != null){
            where.accept(new NamingDeleteExpressionVisitorAdapter(ConditionName.WHERE, this.namingContent));
        }
    }
}
