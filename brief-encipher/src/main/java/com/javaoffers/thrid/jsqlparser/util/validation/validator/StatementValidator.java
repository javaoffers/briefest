/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.util.validation.validator;

import com.javaoffers.thrid.jsqlparser.parser.feature.Feature;
import com.javaoffers.thrid.jsqlparser.statement.*;
import com.javaoffers.thrid.jsqlparser.statement.alter.Alter;
import com.javaoffers.thrid.jsqlparser.statement.alter.AlterSession;
import com.javaoffers.thrid.jsqlparser.statement.alter.AlterSystemStatement;
import com.javaoffers.thrid.jsqlparser.statement.alter.RenameTableStatement;
import com.javaoffers.thrid.jsqlparser.statement.alter.sequence.AlterSequence;
import com.javaoffers.thrid.jsqlparser.statement.comment.Comment;
import com.javaoffers.thrid.jsqlparser.statement.create.function.CreateFunction;
import com.javaoffers.thrid.jsqlparser.statement.create.index.CreateIndex;
import com.javaoffers.thrid.jsqlparser.statement.create.procedure.CreateProcedure;
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
import com.javaoffers.thrid.jsqlparser.util.validation.ValidationCapability;
import com.javaoffers.thrid.jsqlparser.util.validation.metadata.NamedObject;

/**
 * @author gitmotte
 */
public class StatementValidator extends AbstractValidator<Statement> implements StatementVisitor {

    @Override
    public void visit(CreateIndex createIndex) {
        getValidator(CreateIndexValidator.class).validate(createIndex);
    }

    @Override
    public void visit(CreateTable createTable) {
        getValidator(CreateTableValidator.class).validate(createTable);
    }

    @Override
    public void visit(CreateView createView) {
        getValidator(CreateViewValidator.class).validate(createView);
    }

    @Override
    public void visit(AlterView alterView) {
        getValidator(AlterViewValidator.class).validate(alterView);
    }

    @Override
    public void visit(Delete delete) {
        getValidator(DeleteValidator.class).validate(delete);
    }

    @Override
    public void visit(Drop drop) {
        getValidator(DropValidator.class).validate(drop);
    }

    @Override
    public void visit(Insert insert) {
        getValidator(InsertValidator.class).validate(insert);
    }

    @Override
    public void visit(Replace replace) {
        getValidator(ReplaceValidator.class).validate(replace);
    }

    @Override
    public void visit(Select select) {
        validateFeature(Feature.select);

        SelectValidator selectValidator = getValidator(SelectValidator.class);
        if (select.getWithItemsList() != null) {
            select.getWithItemsList().forEach(wi -> wi.accept(selectValidator));
        }
        select.getSelectBody().accept(selectValidator);
    }

    @Override
    public void visit(Truncate truncate) {
        validateFeature(Feature.truncate);
        validateOptionalFromItem(truncate.getTable());
    }

    @Override
    public void visit(Update update) {
        getValidator(UpdateValidator.class).validate(update);
    }

    @Override
    public void visit(Alter alter) {
        getValidator(AlterValidator.class).validate(alter);
    }

    @Override
    public void visit(Statements stmts) {
        stmts.getStatements().forEach(s -> s.accept(this));
    }

    @Override
    public void visit(Execute execute) {
        getValidator(ExecuteValidator.class).validate(execute);
    }

    @Override
    public void visit(SetStatement set) {
        getValidator(SetStatementValidator.class).validate(set);
    }

    @Override
    public void visit(ResetStatement reset) {
        getValidator(ResetStatementValidator.class).validate(reset);
    }

    @Override
    public void visit(Merge merge) {
        getValidator(MergeValidator.class).validate(merge);
    }

    @Override
    public void visit(Commit commit) {
        validateFeature(Feature.commit);
    }

    @Override
    public void visit(Upsert upsert) {
        getValidator(UpsertValidator.class).validate(upsert);
    }

    @Override
    public void visit(UseStatement use) {
        getValidator(UseStatementValidator.class).validate(use);
    }

    @Override
    public void visit(ShowStatement show) {
        getValidator(ShowStatementValidator.class).validate(show);
    }

    @Override
    public void visit(ShowColumnsStatement show) {
        getValidator(ShowColumnsStatementValidator.class).validate(show);
    }

    @Override
    public void visit(ShowTablesStatement showTables) {
        getValidator(ShowTablesStatementValidator.class).validate(showTables);
    }

    @Override
    public void visit(Block block) {
        validateFeature(Feature.block);
        block.getStatements().accept(this);
    }

    @Override
    public void visit(Comment comment) {
        for (ValidationCapability c : getCapabilities()) {
            validateFeature(c, Feature.comment);
            validateOptionalFeature(c, comment.getTable(), Feature.commentOnTable);
            validateOptionalFeature(c, comment.getColumn(), Feature.commentOnColumn);
            validateOptionalFeature(c, comment.getView(), Feature.commentOnView);
        }
    }


    @Override
    public void visit(ValuesStatement values) {
        getValidator(ValuesStatementValidator.class).validate(values);
    }

    @Override
    public void visit(DescribeStatement describe) {
        validateFeature(Feature.describe);
        validateOptionalFromItem(describe.getTable());
    }

    @Override
    public void visit(ExplainStatement explain) {
        validateFeature(Feature.explain);
        explain.getStatement().accept(this);
    }


    @Override
    public void visit(DeclareStatement declare) {
        getValidator(DeclareStatementValidator.class).validate(declare);
    }

    @Override
    public void visit(Grant grant) {
        getValidator(GrantValidator.class).validate(grant);
    }

    @Override
    public void visit(CreateSchema aThis) {
        validateFeatureAndName(Feature.createSchema, NamedObject.schema, aThis.getSchemaName());
        aThis.getStatements().forEach(s -> s.accept(this));
    }

    @Override
    public void visit(CreateSequence createSequence) {
        getValidator(CreateSequenceValidator.class).validate(createSequence);
    }

    @Override
    public void visit(AlterSequence alterSequence) {
        getValidator(AlterSequenceValidator.class).validate(alterSequence);
    }

    @Override
    public void visit(CreateFunctionalStatement createFunctionalStatement) {
        validateFeature(Feature.functionalStatement);
        if (createFunctionalStatement instanceof CreateFunction) {
            validateFeature(Feature.createFunction);
        } else if (createFunctionalStatement instanceof CreateProcedure) {
            validateFeature(Feature.createProcedure);
        }
    }

    @Override
    public void validate(Statement statement) {
        statement.accept(this);
    }

    @Override
    public void visit(CreateSynonym createSynonym) {
        getValidator(CreateSynonymValidator.class).validate(createSynonym);
    }

    @Override
    public void visit(SavepointStatement savepointStatement) {
        //TODO: not yet implemented
    }

    @Override
    public void visit(RollbackStatement rollbackStatement) {
        //TODO: not yet implemented
    }
    
    @Override
    public void visit(AlterSession alterSession) {
        //TODO: not yet implemented
    }

    @Override
    public void visit(IfElseStatement ifElseStatement) {
        ifElseStatement.getIfStatement().accept(this);
        if (ifElseStatement.getElseStatement()!=null) {
            ifElseStatement.getElseStatement().accept(this);
        }
    }

    public void visit(RenameTableStatement renameTableStatement) {
        //TODO: not yet implemented
    }

    @Override
    public void visit(PurgeStatement purgeStatement) {
        //TODO: not yet implemented
    }

    @Override
    public void visit(AlterSystemStatement alterSystemStatement) {
        //TODO: not yet implemented
    }
}
