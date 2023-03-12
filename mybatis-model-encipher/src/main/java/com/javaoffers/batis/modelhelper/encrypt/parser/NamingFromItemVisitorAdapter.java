package com.javaoffers.batis.modelhelper.encrypt.parser;

import com.javaoffers.thrid.jsqlparser.expression.Alias;
import com.javaoffers.thrid.jsqlparser.schema.Table;
import com.javaoffers.thrid.jsqlparser.statement.select.FromItemVisitorAdapter;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectBody;
import com.javaoffers.thrid.jsqlparser.statement.select.SubSelect;

/**
 * @author mingJie
 */

public class NamingFromItemVisitorAdapter extends FromItemVisitorAdapter {

    private NamingSelectContent namingContent;

    public NamingFromItemVisitorAdapter(NamingSelectContent namingContent) {
        this.namingContent = namingContent;
    }

    @Override
    public void visit(Table table) {
        Alias alias = table.getAlias();
        if(alias != null){
            namingContent.putAliasNameAndRealTableName(alias.getName(), table.getName());
        }else {
            //单表查询.并且无别名.()
            namingContent.putAliasNameAndRealTableName(table.getName(), table.getName() );
        }

        //如果是子查询，则记录子查询的主表. (先解析from table, 后解析join table)
        if(namingContent.isSubSelct() && namingContent.getSubSelectMainTable() == null){
            namingContent.setSubSelectMainTable(table.getName());
        }

    }

    public void visit(SubSelect subSelect) {

        SelectBody selectBody = subSelect.getSelectBody();
        NamingSelectContent namingContent = new NamingSelectContent();
        namingContent.setSubSelct(true);
        namingContent.setProcessor(this.namingContent.getProcessor());
        //这里直接使用 namingContent.中的mapper。 不用新建因为from xx join (select xx) aliasName
        //在最后的where时可能会用到这里的join 子表别名
        namingContent.setTableNameMapper(this.namingContent.getTableNameMapper());
        selectBody.accept(new NamingSelectVisitorAdapter(namingContent));

    }

    public NamingSelectContent getNamingContent() {
        return namingContent;
    }

    public void setNamingContent(NamingSelectContent namingContent) {
        this.namingContent = namingContent;
    }
}
