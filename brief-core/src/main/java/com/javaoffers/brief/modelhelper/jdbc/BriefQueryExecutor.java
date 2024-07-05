package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.core.SQL;
import com.javaoffers.brief.modelhelper.exception.ParseResultSetException;
import com.javaoffers.brief.modelhelper.exception.SqlParseException;
import com.javaoffers.brief.modelhelper.parse.ModelParseUtils;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.apache.commons.collections4.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: create by cmj on 2023/7/29 18:48
 */
public class BriefQueryExecutor<T> implements QueryExecutor<T> {

    DataSource dataSource;

    Class<T> modelClass;

    public BriefQueryExecutor(DataSource dataSource, Class modelClass) {
        this.dataSource = dataSource;
        this.modelClass = modelClass;
    }

    @Override
    public T query(BaseSQLInfo sql) {
        List<T> ts = this.queryList(sql);
        if (CollectionUtils.isEmpty(ts)) {
            return null;
        }
        return ts.get(0);
    }

    @Override
    public List<T> queryList(BaseSQLInfo sql) {
        boolean oldAutoCommitStatus = false;
        Connection connection = null;
        try {

            connection = getConnection();
            oldAutoCommitStatus = connection.getAutoCommit();
            PreparedStatement ps = connection.prepareStatement(sql.getSql());
            List<Object[]> argsParam = sql.getArgsParam();
            if (argsParam != null && argsParam.size() == 1) {
                Object[] ov = argsParam.get(0);
                for (int i = 0; i < ov.length; ) {
                    Object o = ov[i];
                    ps.setObject(++i, o);
                }
            }
            switch (sql.getSqlType()) {
                case JOIN_SELECT:
                    return ModelParseUtils.converterResultSet2ModelForJoinSelect(this.modelClass,
                            new BriefResultSetExecutor(ps.executeQuery()));
                case NORMAL_SELECT:
                    return ModelParseUtils.converterResultSet2ModelForNormalSelect(this.modelClass,
                            new BriefResultSetExecutor(ps.executeQuery()));
                case DML:
                case DDL:
                    boolean execute = ps.execute();
                    List dmlResult = Lists.newArrayList();
                    if (execute) {
                        //NOTE: RESULT TYPE OF STRING
                        ResultSet rs = ps.getResultSet();
                        while (rs.next()) {
                            List dmlCol = Lists.newArrayList();
                            int columnCount = rs.getMetaData().getColumnCount();
                            for (int i = 1; i <= columnCount; i++) {
                                dmlCol.add(rs.getString(i));
                            }
                            if (dmlCol.size() > 0) {
                                dmlResult.add(dmlCol);
                            }
                        }
                    } else {
                        //true Indicates successful execution
                        dmlResult.add(true);
                    }
                    return dmlResult;
                default:
                    throw new ParseResultSetException("sql type does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SqlParseException(e.getMessage());
        } finally {
            closeConnection(connection, oldAutoCommitStatus);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SqlParseException(e.getMessage());
        }

    }
}
