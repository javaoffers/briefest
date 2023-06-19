package com.javaoffers.brief.modelhelper.parser;

import com.javaoffers.thrid.jsqlparser.expression.Alias;
import com.javaoffers.thrid.jsqlparser.schema.Table;
import com.javaoffers.thrid.jsqlparser.statement.select.FromItemVisitorAdapter;
import com.javaoffers.thrid.jsqlparser.statement.select.SelectBody;
import com.javaoffers.thrid.jsqlparser.statement.select.SubSelect;

import java.util.HashMap;

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
            //Single table query. And no alias.()
            namingContent.putAliasNameAndRealTableName(table.getName(), table.getName() );
        }

        //If it is a subquery, record the main table of the subquery. (First parse the from table, then parse the join table)
        if(namingContent.isSubSelct() && namingContent.getSubSelectMainTable() == null){
            namingContent.setSubSelectMainTable(table.getName());
        }

    }

    /**
     * join (select xx from table where xx ) alias on xxx
     * @param subSelect
     */
    public void visit(SubSelect subSelect) {

        SelectBody selectBody = subSelect.getSelectBody();
        NamingSelectContent namingContent = new NamingSelectContent();
        namingContent.setSubSelct(true);
        namingContent.setProcessor(this.namingContent.getProcessor());
        //Here you need to create a new TableNameMapper. Because from xx join (select xx) aliasName. The subquery has been completely decrypted.
        //Accessing the query field of the subquery in on does not need to be decrypted, because it has been decrypted in the subquery
        //That is, the alias of the subquery used in where to access the data does not need to be decrypted. Because it has been decrypted in the subquery
        namingContent.setTableNameMapper(new HashMap<>(this.namingContent.getTableNameMapper()));
        selectBody.accept(new NamingSelectVisitorAdapter(namingContent));

    }

    public NamingSelectContent getNamingContent() {
        return namingContent;
    }

    public void setNamingContent(NamingSelectContent namingContent) {
        this.namingContent = namingContent;
    }
}
