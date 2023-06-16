package com.javaoffers.brief.modelhelper.encrypt.parser.delete;


import com.javaoffers.brief.modelhelper.encrypt.parser.ColNameProcessorInfo;
import com.javaoffers.brief.modelhelper.encrypt.parser.ConditionName;
import com.javaoffers.brief.modelhelper.encrypt.parser.NamingSelectContent;
import com.javaoffers.brief.modelhelper.encrypt.parser.NamingSelectVisitorAdapter;
import com.javaoffers.thrid.jsqlparser.expression.ExpressionVisitorAdapter;
import com.javaoffers.thrid.jsqlparser.schema.Column;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectBody;
import com.javaoffers.thrid.jsqlparser.statement.select.SubSelect;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author mingJie
 */
public class NamingDeleteExpressionVisitorAdapter extends ExpressionVisitorAdapter {

    private ConditionName conditionName;
    private NamingSelectContent namingContent;

    /**
     * 处理wehere colName = ? and xxx = xxx
     * @param column
     */
    @Override
    public void visit(Column column) {
        if(ConditionName.isWhereOnName(this.conditionName)){
            String columnName = column.getColumnName();
            String deleteTableName = this.namingContent.getDeleteTableName();
            if(this.namingContent.isProcessorCloName(deleteTableName, columnName)){
                ColNameProcessorInfo colNameProcessorInfo = new ColNameProcessorInfo();
                colNameProcessorInfo.setColumn(column);
                colNameProcessorInfo.setConditionName(this.conditionName);
                this.namingContent.getProcessorByTableName(deleteTableName).accept(colNameProcessorInfo);
            }
        }

    }

    /**
     * 如果where中存在子查询.
     */
    @Override
    public void visit(SubSelect subSelect) {
        SelectBody selectBody = subSelect.getSelectBody();
        //创建一个新的上下文对象给子查询语句使用
        NamingSelectContent namingContent = new NamingSelectContent();
        namingContent.setSubSelct(true);
        Map<String, String> tableNameMapper = this.namingContent.getTableNameMapper();
        tableNameMapper.forEach((k, v) -> {
            if (StringUtils.isNotBlank(k)) {
                namingContent.getTableNameMapper().put(k, v);
            }
        });

        namingContent.setProcessor(this.namingContent.getProcessor());
        selectBody.accept(new NamingSelectVisitorAdapter(namingContent));
    }

    public NamingDeleteExpressionVisitorAdapter(ConditionName name, NamingSelectContent namingContent) {
        this.conditionName = name;
        this.namingContent = namingContent;
    }
}
