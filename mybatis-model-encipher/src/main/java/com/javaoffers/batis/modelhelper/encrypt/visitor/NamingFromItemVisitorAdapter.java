package com.javaoffers.batis.modelhelper.encrypt.visitor;


import com.javaoffers.batis.modelhelper.encrypt.NamingContent;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * @author mingJie
 */

public class NamingFromItemVisitorAdapter extends FromItemVisitorAdapter {

    private NamingContent namingContent;

    public NamingFromItemVisitorAdapter(NamingContent namingContent) {
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
        if(namingContent.isSubSelect() && namingContent.getSubSelectMainTable() == null){
            namingContent.setSubSelectMainTable(table.getName());
        }

    }

    public void visit(SubSelect subSelect) {

        SelectBody selectBody = subSelect.getSelectBody();
        NamingContent namingContent = new NamingContent();
        namingContent.setSubSelect(true);
        namingContent.setProcessor(this.namingContent.getProcessor());
        //这里直接使用 namingContent.中的mapper。 不用新建因为from xx join (select xx) aliasName
        //在最后的where时可能会用到这里的join 子表别名
        namingContent.setTableNameMapper(this.namingContent.getTableNameMapper());
        selectBody.accept(new NamingSelectVisitorAdapter(namingContent));

    }





}
