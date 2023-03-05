package com.javaoffers.batis.modelhelper.encrypt.visitor;

import com.javaoffers.batis.modelhelper.encrypt.ColNameProcessorInfo;
import com.javaoffers.batis.modelhelper.encrypt.ConditionName;
import com.javaoffers.batis.modelhelper.encrypt.NamingContent;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author mingJie
 */
public class NamingExpressionVisitorAdapter extends ExpressionVisitorAdapter {

    private ConditionName conditionName;
    private NamingContent namingContent;


    public void visit(Column column) {

        String columnName = column.getColumnName();
        String tableName = "";
        Table table = column.getTable();
        if (table != null) {
            tableName = table.getName();
        }
        //获取真实的表名
        Map<String, String> tableNameMapper = namingContent.getTableNameMapper();
        tableName = tableNameMapper.getOrDefault(tableName, tableName);
        //LOGUtils.printLog(this.conditionName + ": " + tableName + "." + columnName);
        ColNameProcessorInfo colNameProcessorInfo = new ColNameProcessorInfo();
        colNameProcessorInfo.setColumn(column);
        colNameProcessorInfo.setConditionName(conditionName);
        // 如果存在tableName不为空
        if (StringUtils.isNotBlank(tableName)) {
            //如果表与checkTableName相同，colName也相同
            if(namingContent.isProcessorTableName(tableName) && namingContent.isProcessorCloName(tableName, columnName)){
                colNameProcessorInfo.setTableName(tableName);
                namingContent.getProcessorByTableName(tableName).accept(colNameProcessorInfo);
                //column.setColumnName("NAME");
            }
        }
        //普通的单表查询
        else if(!namingContent.isHavaJoin()
                && !namingContent.isSubSelect()
                && namingContent.isProcessorCloName(namingContent.getSimpleSingleTable(), columnName)
        ){
            colNameProcessorInfo.setTableName(namingContent.getSimpleSingleTable());
            namingContent.getProcessorByTableName(namingContent.getSimpleSingleTable()).accept(colNameProcessorInfo);
            //column.setColumnName("NAME");

        }

        // 如果是没有join的子查询. 并且子查询的表与checkTableName相同 (select colName 部分)
        else if (namingContent.isSubSelect()
                && !namingContent.isHavaJoin()
                && ConditionName.SELECT_COL_NAME == conditionName
                && namingContent.isProcessorCloName(namingContent.getSubSelectMainTable(), columnName)
                ) {
            colNameProcessorInfo.setTableName(namingContent.getSubSelectMainTable());
            namingContent.getProcessorByTableName(namingContent.getSubSelectMainTable()).accept(colNameProcessorInfo);
            //column.setColumnName("NAME");

        }
        // 如果是没有join的子查询. 并且子查询的where部分需要判断是否包含checkTableName
        // (where 部分 可能存在主表的colName, 这些表（主表和子表）是否是需要检查的表)
        else if (namingContent.isSubSelect()
                && !namingContent.isHavaJoin()
                && ConditionName.WHERE == conditionName
                && namingContent.isContainsProcessorRealTableName()
                ) {
            //这些表（主表和子表）, 正确来讲只会属于其中一个表， 如果同时属于多个表则sql是错误的
            namingContent.getTableNameMapper().values().forEach(tableName0->{
                if(namingContent.isProcessorCloName(tableName0, columnName )){
                    colNameProcessorInfo.setTableName(tableName0);
                    namingContent.getProcessorByTableName(tableName0).accept(colNameProcessorInfo);
                    return;
                }
            });

            //column.setColumnName("NAME");

        }
        // 如果是多表.
        else if (namingContent.isHavaJoin()
                &&namingContent.isContainsProcessorRealTableName()
                 ) {
            // join 多表中存在要处理的表
            namingContent.getTableNameMapper().values().forEach(tableName0->{
                if(namingContent.isProcessorCloName(tableName0, columnName )){
                    colNameProcessorInfo.setTableName(tableName0);
                    namingContent.getProcessorByTableName(tableName0).accept(colNameProcessorInfo);
                    return;
                }
            });
            //column.setColumnName("NAME");
        }


    }

    @Override
    public void visit(SubSelect subSelect) {
        SelectBody selectBody = subSelect.getSelectBody();
        NamingContent namingContent = new NamingContent();
        namingContent.setSubSelect(true);
        Map<String, String> tableNameMapper = this.namingContent.getTableNameMapper();
        tableNameMapper.forEach((k, v) -> {
            if (StringUtils.isNotBlank(k)) {
                namingContent.getTableNameMapper().put(k, v);
            }
        });
        namingContent.setProcessor(this.namingContent.getProcessor());
        selectBody.accept(new NamingSelectVisitorAdapter(namingContent));
    }

    public NamingExpressionVisitorAdapter(ConditionName name, NamingContent namingContent) {
        this.conditionName = name;
        this.namingContent = namingContent;
    }
}
