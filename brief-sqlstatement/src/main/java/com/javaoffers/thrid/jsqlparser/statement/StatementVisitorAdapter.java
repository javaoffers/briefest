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

@SuppressWarnings({"PMD.UncommentedEmptyMethodBody"})
public class StatementVisitorAdapter implements StatementVisitor {

    @Override
    public void visit(Comment comment) {

    }

    @Override
    public void visit(Commit commit) {

    }

    @Override
    public void visit(Select select) {

    }

    @Override
    public void visit(Delete delete) {

    }

    @Override
    public void visit(Update update) {

    }

    @Override
    public void visit(Insert insert) {

    }

    @Override
    public void visit(Replace replace) {

    }

    @Override
    public void visit(Drop drop) {

    }

    @Override
    public void visit(Truncate truncate) {

    }

    @Override
    public void visit(CreateIndex createIndex) {

    }

    @Override
    public void visit(CreateSchema aThis) {
    }

    @Override
    public void visit(CreateTable createTable) {

    }

    @Override
    public void visit(CreateView createView) {

    }

    @Override
    public void visit(Alter alter) {

    }

    @Override
    public void visit(Statements stmts) {
        for (Statement statement : stmts.getStatements()) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(Execute execute) {

    }

    @Override
    public void visit(SetStatement set) {

    }

    @Override
    public void visit(ResetStatement reset) {

    }

    @Override
    public void visit(Merge merge) {

    }

    @Override
    public void visit(AlterView alterView) {
    }

    @Override
    public void visit(Upsert upsert) {
    }

    @Override
    public void visit(UseStatement use) {
    }

    @Override
    public void visit(Block block) {
    }

    @Override
    public void visit(ValuesStatement values) {
    }

    @Override
    public void visit(DescribeStatement describe) {
    }

    @Override
    public void visit(ExplainStatement aThis) {
    }

    @Override
    public void visit(ShowStatement aThis) {
    }

    @Override
    public void visit(ShowColumnsStatement set) {
    }

    @Override
    public void visit(ShowTablesStatement showTables) {
    }

    @Override
    public void visit(DeclareStatement aThis) {
    }

    @Override
    public void visit(Grant grant) {
    }

    @Override
    public void visit(CreateSequence createSequence) {
    }

    @Override
    public void visit(AlterSequence alterSequence) {
    }

    @Override
    public void visit(CreateFunctionalStatement createFunctionalStatement) {
    }

    @Override
    public void visit(CreateSynonym createSynonym) {
    }

    @Override
    public void visit(SavepointStatement savepointStatement) {
        //@todo: do something usefull here
    }

    @Override
    public void visit(RollbackStatement rollbackStatement) {
        //@todo: do something usefull here
    }
    @Override
    public void visit(AlterSession alterSession) {
       //@todo: do something usefull here
    }

    @Override
    public void visit(IfElseStatement ifElseStatement) {
        ifElseStatement.getIfStatement().accept(this);
        if (ifElseStatement.getElseStatement()!=null) {
            ifElseStatement.getElseStatement().accept(this);
          }
      }
        
    @Override
    public void visit(RenameTableStatement renameTableStatement) {
        //@todo: do something usefull here
    }

    @Override
    public void visit(PurgeStatement purgeStatement) {
        //@todo: do something usefull here
    }

    @Override
    public void visit(AlterSystemStatement alterSystemStatement) {
    }
}
