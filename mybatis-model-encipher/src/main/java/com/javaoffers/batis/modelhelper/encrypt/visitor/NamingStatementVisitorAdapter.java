package com.javaoffers.batis.modelhelper.encrypt.visitor;

import com.javaoffers.batis.modelhelper.encrypt.NamingContent;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.update.Update;

/**
 * @author mingJie
 */
public class NamingStatementVisitorAdapter extends StatementVisitorAdapter {

    private NamingContent namingContent;

    public NamingStatementVisitorAdapter(NamingContent namingContent) {
        this.namingContent = namingContent;
    }

    @Override
    public void visit(Select select) {
        SelectBody selectBody = select.getSelectBody();
        selectBody.accept(new NamingSelectVisitorAdapter(this.namingContent));
    }

    @Override
    public void visit(Update update) {

    }

    @Override
    public void visit(Insert insert) {

    }
}
