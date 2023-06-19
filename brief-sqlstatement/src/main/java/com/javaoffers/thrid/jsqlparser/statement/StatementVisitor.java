/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.statement;

import com.javaoffers.thrid.jsqlparser.statement.alter.Alter;
import com.javaoffers.thrid.jsqlparser.statement.alter.AlterSession;
import com.javaoffers.thrid.jsqlparser.statement.alter.AlterSystemStatement;
import com.javaoffers.thrid.jsqlparser.statement.alter.RenameTableStatement;
import com.javaoffers.thrid.jsqlparser.statement.alter.sequence.AlterSequence;
import com.javaoffers.thrid.jsqlparser.statement.comment.Comment;
import com.javaoffers.thrid.jsqlparser.statement.create.index.CreateIndex;
import com.javaoffers.thrid.jsqlparser.statement.create.schema.CreateSchema;
import com.javaoffers.thrid.jsqlparser.statement.create.sequence.CreateSequence;
import com.javaoffers.thrid.jsqlparser.statement.create.synonym.CreateSynonym;
import com.javaoffers.thrid.jsqlparser.statement.create.table.CreateTable;
import com.javaoffers.thrid.jsqlparser.statement.create.view.AlterView;
import com.javaoffers.thrid.jsqlparser.statement.create.view.CreateView;
import com.javaoffers.thrid.jsqlparser.statement.delete.Delete;
import com.javaoffers.thrid.jsqlparser.statement.drop.Drop;
import com.javaoffers.thrid.jsqlparser.statement.execute.Execute;
import com.javaoffers.thrid.jsqlparser.statement.grant.Grant;
import com.javaoffers.thrid.jsqlparser.statement.insert.Insert;
import com.javaoffers.thrid.jsqlparser.statement.merge.Merge;
import com.javaoffers.thrid.jsqlparser.statement.replace.Replace;
import com.javaoffers.thrid.jsqlparser.statement.select.Select;
import com.javaoffers.thrid.jsqlparser.statement.show.ShowTablesStatement;
import com.javaoffers.thrid.jsqlparser.statement.truncate.Truncate;
import com.javaoffers.thrid.jsqlparser.statement.update.Update;
import com.javaoffers.thrid.jsqlparser.statement.upsert.Upsert;
import com.javaoffers.thrid.jsqlparser.statement.values.ValuesStatement;

public interface StatementVisitor {
    
    void visit(SavepointStatement savepointStatement);
    
    void visit(RollbackStatement rollbackStatement);

    void visit(Comment comment);

    void visit(Commit commit);

    void visit(Delete delete);

    void visit(Update update);

    void visit(Insert insert);

    void visit(Replace replace);

    void visit(Drop drop);

    void visit(Truncate truncate);

    void visit(CreateIndex createIndex);

    void visit(CreateSchema aThis);

    void visit(CreateTable createTable);

    void visit(CreateView createView);

    void visit(AlterView alterView);

    void visit(Alter alter);

    void visit(Statements stmts);

    void visit(Execute execute);

    void visit(SetStatement set);

    void visit(ResetStatement reset);

    void visit(ShowColumnsStatement set);

    void visit(ShowTablesStatement showTables);

    void visit(Merge merge);

    void visit(Select select);

    void visit(Upsert upsert);

    void visit(UseStatement use);

    void visit(Block block);

    void visit(ValuesStatement values);

    void visit(DescribeStatement describe);

    void visit(ExplainStatement aThis);

    void visit(ShowStatement aThis);

    void visit(DeclareStatement aThis);

    void visit(Grant grant);

    void visit(CreateSequence createSequence);

    void visit(AlterSequence alterSequence);

    void visit(CreateFunctionalStatement createFunctionalStatement);

    void visit(CreateSynonym createSynonym);

    void visit(AlterSession alterSession);

    void visit(IfElseStatement aThis);
    void visit(RenameTableStatement renameTableStatement);

    void visit(PurgeStatement purgeStatement);

    void visit(AlterSystemStatement alterSystemStatement);
}
