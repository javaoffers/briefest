/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2020 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package com.javaoffers.thrid.jsqlparser.parser.feature;

import com.javaoffers.thrid.jsqlparser.expression.*;
import com.javaoffers.thrid.jsqlparser.expression.operators.relational.SupportsOldOracleJoinSyntax;
import com.javaoffers.thrid.jsqlparser.statement.*;
import com.javaoffers.thrid.jsqlparser.statement.alter.Alter;
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
import com.javaoffers.thrid.jsqlparser.statement.merge.Merge;
import com.javaoffers.thrid.jsqlparser.statement.replace.Replace;
import com.javaoffers.thrid.jsqlparser.statement.select.*;
import com.javaoffers.thrid.jsqlparser.statement.show.ShowTablesStatement;
import com.javaoffers.thrid.jsqlparser.statement.truncate.Truncate;
import com.javaoffers.thrid.jsqlparser.statement.update.Update;
import com.javaoffers.thrid.jsqlparser.statement.upsert.Upsert;
import com.javaoffers.thrid.jsqlparser.statement.values.ValuesStatement;

public enum Feature {

    // SQL KEYWORD FEATURES
    /**
     * "SELECT"
     */
    select,
    /**
     * "GROUP BY"
     */
    selectGroupBy,
    /**
     * "GROUPING SETS"
     */
    selectGroupByGroupingSets,
    /**
     * "HAVING"
     */
    selectHaving,
    /**
     * "INTO table(, table)*"
     */
    selectInto,

    /**
     * @see Limit
     */
    limit,
    /**
     * "LIMIT NULL"
     *
     * @see Limit#isLimitNull()
     */
    limitNull,
    /**
     * "LIMIT ALL"
     *
     * @see Limit#isLimitAll()
     */
    limitAll,
    /**
     * "LIMIT offset, limit"
     *
     * @see Limit#getOffset()
     */
    limitOffset,
    /**
     * "OFFSET offset"
     * @see Offset
     */
    offset,
    /**
     * "OFFSET offset param" where param is ROW | ROWS
     *
     * @see Offset#getOffsetParam()
     */
    offsetParam,

    /**
     * @see Fetch
     */
    fetch,
    /**
     * "FETCH FIRST row_count (ROW | ROWS) ONLY"
     * @see Fetch#isFetchParamFirst()
     */
    fetchFirst,
    /**
     * "FETCH NEXT row_count (ROW | ROWS) ONLY"
     * if not {@link #fetchFirst}
     *
     * @see Fetch#isFetchParamFirst()
     */
    fetchNext,

    /**
     * "JOIN"
     */
    join,
    /**
     * join tables by ", OUTER" placing the join specification in WHERE-clause
     */
    joinOuterSimple,
    /**
     * join tables by "," placing the join specification in WHERE-clause
     */
    joinSimple,
    /**
     * "RIGHT" join
     */
    joinRight,
    /**
     * "NATURAL" join
     */
    joinNatural,
    /**
     * "FULL" join
     */
    joinFull,
    /**
     * "LEFT" join
     */
    joinLeft,
    /**
     * "CROSS" join
     */
    joinCross,
    /**
     * "OUTER" join
     */
    joinOuter,
    /**
     * "SEMI" join
     */
    joinSemi,
    /**
     * "INNER" join
     */
    joinInner,
    /**
     * "STRAIGHT_JOIN" join
     */
    joinStraight,
    /**
     * "APPLY" join
     */
    joinApply,

    joinWindow,
    joinUsingColumns,

    /**
     * "SKIP variable" | "SKIP ?" | "SKIP rowCount"
     *
     * @see Skip
     */
    skip,
    /**
     * "FIRST" \?|[0-9]+|variable
     * or
     * "LIMIT" \?|[0-9]+|variable
     *
     * @see First
     */
    first,
    /**
     * "TOP" ? "PERCENT"
     *
     * @see Top
     */
    top,
    /**
     * "OPTIMIZE FOR rowCount ROWS"
     *
     * @see OptimizeFor
     */
    optimizeFor,

    /**
     * "UNIQUE" keyword
     */
    selectUnique,
    /**
     * "DISTINCT" keyword
     */
    distinct,
    /**
     * "DISTINCT ON (col1, ...)"
     */
    distinctOn,

    /**
     * "ORDER BY"
     */
    orderBy,
    /**
     * "ORDER BY com.javaoffers.thrid.sqlparse.expression [ NULLS { FIRST | LAST } ]"
     */
    orderByNullOrdering,

    /**
     * "FOR UPDATE"
     */
    selectForUpdate,
    /**
     * "FOR UPDATE OF table"
     */
    selectForUpdateOfTable,
    /**
     * "FOR UPDATE WAIT timeout"
     */
    selectForUpdateWait,
    /**
     * "FOR UPDATE NOWAIT"
     */
    selectForUpdateNoWait,


    /**
     * SQL "INSERT" com.javaoffers.thrid.sqlparse.statement is allowed
     */
    insert,
    /**
     * "INSERT .. SELECT"
     */
    insertFromSelect,
    /**
     * "LOW_PRIORITY | DELAYED | HIGH_PRIORITY | IGNORE"
     */
    insertModifierPriority,
    /**
     * "IGNORE"
     */
    insertModifierIgnore,
    /**
     * "INSERT .. SET"
     */
    insertUseSet,
    /**
     * "ON DUPLICATE KEY UPDATE"
     */
    insertUseDuplicateKeyUpdate,
    /**
     * "RETURNING *"
     */
    insertReturningAll,
    /**
     * "RETURNING expr(, expr)*"
     *
     * @see SelectExpressionItem
     */
    insertReturningExpressionList,

    /**
     * "VALUES"
     */
    insertValues,
    /**
     * @see ValuesStatement
     */
    values,

    /**
     * SQL "UPDATE" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Update
     */
    update,
    /**
     * "UPDATE table1 SET ... FROM table2
     */
    updateFrom,
    /**
     * "UPDATE table1, table2 ..."
     */
    updateJoins,
    /**
     * UPDATE table SET (col, ...) = (SELECT col, ... )"
     */
    updateUseSelect,
    updateOrderBy,
    updateLimit,
    updateReturning,
    /**
     * SQL "DELETE" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Delete
     */
    delete,
    /**
     * "DELETE FROM table1, table1 ..."
     */
    deleteJoin,
    /**
     * "DELETE table1, table1 FROM table ..."
     */
    deleteTables,
    /**
     * "LIMIT row_count"
     */
    deleteLimit,
    /**
     * "ORDER BY ..."
     */
    deleteOrderBy,

    /**
     * SQL "UPSERT" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Upsert
     * @see <a href=
     *      "https://wiki.postgresql.org/wiki/UPSERT">https://wiki.postgresql.org/wiki/UPSERT</a>
     */
    upsert,
    /**
     * SQL "MERGE" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Merge
     */
    merge,

    /**
     * SQL "ALTER" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Alter
     */
    alterTable,
    /**
     * SQL "ALTER SEQUENCE" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see AlterSequence
     */
    alterSequence,
    /**
     * SQL "ALTER VIEW" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see AlterView
     */
    alterView,
    /**
     * SQL "REPLACE VIEW" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see AlterView
     */
    alterViewReplace,
    /**
     * SQL "ALTER INDEX" com.javaoffers.thrid.sqlparse.statement is allowed
     */
    alterIndex,

    /**
     * SQL "TRUNCATE" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Truncate
     */
    truncate,
    /**
     * SQL "CALL|EXEC|EXECUTE" stored procedure is allowed
     *
     * @see Execute
     */
    execute,
    executeExec, executeCall, executeExecute,

    /**
     * SQL "EXECUTE" com.javaoffers.thrid.sqlparse.statement is allowed
     */
    executeStatement,
    /**
     * SQL "EXECUTE IMMEDIATE" com.javaoffers.thrid.sqlparse.statement is allowed
     */
    executeStatementImmediate,

    executeUsing,
    /**
     * SQL "REPLACE" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Replace
     */
    replace,
    /**
     * SQL "DROP" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Drop
     */
    drop,
    dropTable,
    dropIndex,
    dropView,
    dropSchema,
    dropSequence,
    dropTableIfExists, dropIndexIfExists, dropViewIfExists, dropSchemaIfExists, dropSequenceIfExists,

    /**
     * SQL "CREATE SCHEMA" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see CreateSchema
     */
    createSchema,
    /**
     * SQL "CREATE VIEW" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see CreateView
     */
    createView,
    /**
     * "CREATE FORCE VIEW"
     */
    createViewForce,
    /**
     * "CREATE TEMPORARAY VIEW"
     */
    createViewTemporary,
    /**
     * "CREATE OR REPLACE VIEW"
     */
    createOrReplaceView,
    /**
     * SQL "CREATE MATERIALIZED VIEW" com.javaoffers.thrid.sqlparse.statement is allowed
     */
    createViewMaterialized,
    /**
     * SQL "CREATE TABLE" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see CreateTable
     */
    createTable,
    /**
     * "CREATE GLOBAL UNLOGGED"
     */
    createTableUnlogged,
    /**
     * i.e. "CREATE GLOBAL TEMPORARY TABLE", "CREATE SHARDED TABLE"
     */
    createTableCreateOptionStrings,
    /**
     * i.e. "ENGINE = InnoDB AUTO_INCREMENT = 8761 DEFAULT CHARSET = utf8"
     */
    createTableTableOptionStrings,
    /**
     * "CREATE TABLE IF NOT EXISTS table"
     */
    createTableIfNotExists,
    /**
     * " ROW MOVEMENT"
     */
    createTableRowMovement,
    /**
     * "CREATE TABLE (colspec) SELECT ... 
     */
    createTableFromSelect,
    /**
     * SQL "CREATE INDEX" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see CreateIndex
     */
    createIndex,
    /**
     * SQL "CREATE SEQUENCE" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see CreateSequence
     */
    createSequence,
    /**
     * SQL "CREATE SYNONYM" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see CreateSynonym
     */
    createSynonym,
    /**
     * SQL "CREATE TRIGGER" com.javaoffers.thrid.sqlparse.statement is allowed
     */
    createTrigger,
    /**
     * SQL "COMMIT" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Commit
     */
    commit,
    /**
     * SQL "COMMENT ON" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Comment
     */
    comment,
    /**
     * "COMMENT ON table"
     */
    commentOnTable,
    /**
     * "COMMENT ON column"
     */
    commentOnColumn,
    /**
     * "COMMENT ON view"
     */
    commentOnView,

    /**
     * SQL "DESCRIBE" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see DescribeStatement
     */
    describe,
    /**
     * SQL "EXPLAIN" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see ExplainStatement
     */
    explain,
    /**
     * @see ShowStatement
     */
    show,
    /**
     * @see ShowTablesStatement
     */
    showTables,
    /**
     * @see ShowColumnsStatement
     */
    showColumns,
    /**
     * @see UseStatement
     */
    use,
    /**
     * @see Grant
     */
    grant,
    /**
     * @see Function
     */
    function,
    /**
     * @see CreateFunction
     */
    createFunction,
    /**
     * @see CreateProcedure
     */
    createProcedure,
    /**
     * @see CreateFunctionalStatement
     */
    functionalStatement,
    /**
     * SQL block starting with "BEGIN" and ends with "END" com.javaoffers.thrid.sqlparse.statement is allowed
     *
     * @see Block
     */
    block,
    /**
     * @see DeclareStatement
     */
    declare,
    /**
     * @see SetStatement
     */
    set,
    /**
     * @see ResetStatement
    */
    reset,
    /**
     * @see Pivot
     */
    pivot,
    /**
     * @see UnPivot
     */
    unpivot,
    /**
     * @see PivotXml
     */
    pivotXml,

    setOperation,
    setOperationUnion,
    setOperationIntersect,
    setOperationExcept,
    setOperationMinus,

    /**
     * "WITH name query"
     */
    withItem,
    withItemRecursive,

    lateralSubSelect,
    /**
     * @see ValuesList
     */
    valuesList,
    /**
     * @see TableFunction
     */
    tableFunction,

    // JDBC
    /**
     * @see JdbcParameter
     */
    jdbcParameter,
    /**
     * @see JdbcNamedParameter
     */
    jdbcNamedParameter,

    // EXPRESSIONS
    /**
     * "LIKE"
     */
    exprLike,
    /**
     * "SIMILAR TO"
     */
    exprSimilarTo,

    // VENDOR SPECIFIC SYNTAX FEATURES

    /**
     * @see KSQLWindow
     */
    kSqlWindow,

    // ORACLE

    /**
     * allows old oracle join syntax (+)
     *
     * @see SupportsOldOracleJoinSyntax
     */
    oracleOldJoinSyntax,
    /**
     * allows oracle prior position
     *
     * @see SupportsOldOracleJoinSyntax
     */
    oraclePriorPosition,
    /**
     * @see OracleHint
     */
    oracleHint,
    /**
     * oracle SQL "CONNECT BY"
     *
     * @see OracleHierarchicalExpression
     */
    oracleHierarchicalExpression,
    oracleOrderBySiblings,

    // MYSQL

    mySqlHintStraightJoin,
    mysqlSqlCacheFlag,
    mysqlCalcFoundRows,

    // SQLSERVER

    /**
     * "FOR XML PATH(...)"
     */
    selectForXmlPath,

    /**
     * allows square brackets for names, disabled by default
     */
    allowSquareBracketQuotation(false),

    /**
       allow parsing of RDBMS specific syntax by switching off SQL Standard Compliant Syntax
    */
    allowPostgresSpecificSyntax(false),

    // PERFORMANCE
    
    /**
     * allows complex com.javaoffers.thrid.sqlparse.expression parameters or named parameters for functions
     * will be switched off, when deep nesting of functions is detected
     */
     allowComplexParsing(true)
    ;

    private Object value;
    private boolean configurable;

    /**
     * a feature which can't configured within the com.javaoffers.thrid.sqlparse.parser
     */
    Feature() {
        this.value = null;
        this.configurable = false;
    }

    /**
     * a feature which can be configured by {@link FeatureConfiguration}
     *
     * @param value
     */
    Feature(Object value) {
        this.value = value;
        this.configurable = true;
    }

    public Object getDefaultValue() {
        return value;
    }

    public boolean isConfigurable() {
        return configurable;
    }

}
