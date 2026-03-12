package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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
        PreparedStatement ps=null;
        ResultSet rs = null;
        try {

            connection = getConnection();
            oldAutoCommitStatus = connection.getAutoCommit();
            ps = connection.prepareStatement(sql.getSql());
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
                    rs = ps.executeQuery();
                    return ModelParseUtils.converterResultSet2ModelForJoinSelect(this.modelClass,
                            new BriefResultSetExecutor(rs));
                case NORMAL_SELECT:
                    rs = ps.executeQuery();
                    return ModelParseUtils.converterResultSet2ModelForNormalSelect(this.modelClass,
                            new BriefResultSetExecutor(rs));
                case DML:
                    if (ps.execute()) {
                        //NOTE: RESULT TYPE OF STRING
                        rs = ps.getResultSet();
                        while (rs.next()) {
                            List<Object> dmlCol = Lists.newArrayList();
                            int columnCount = rs.getMetaData().getColumnCount();
                            for (int i = 1; i <= columnCount; i++) {
                                dmlCol.add(rs.getObject(i));
                            }
                            if (!dmlCol.isEmpty()) {
                                sql.getStreaming().accept(dmlCol);
                            }
                        }
                    } else {
                        int updateCount = ps.getUpdateCount();
                        if(updateCount!=-1){
                            sql.getStreaming().accept(updateCount);
                        }else{
                            //true Indicates successful execution
                            sql.getStreaming().accept(true);
                        }
                    }
                    return new ArrayList<>();
                case DDL:
                    boolean execute = ps.execute();
                    List ddlResult = Lists.newArrayList();
                    if (execute) {
                        //NOTE: RESULT TYPE OF STRING
                        rs = ps.getResultSet();
                        while (rs.next()) {
                            List dmlCol = Lists.newArrayList();
                            int columnCount = rs.getMetaData().getColumnCount();
                            for (int i = 1; i <= columnCount; i++) {
                                dmlCol.add(rs.getString(i));
                            }
                            if (dmlCol.size() > 0) {
                                ddlResult.add(dmlCol);
                            }
                        }
                    } else {
                        int updateCount = ps.getUpdateCount();
                        if(updateCount!=-1){
                            ddlResult.add(updateCount);
                        }else{
                            //true Indicates successful execution
                            ddlResult.add(true);
                        }
                    }
                    return ddlResult;
                default:
                    throw new ParseResultSetException("sql type does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SqlParseException(e.getMessage());
        } finally {
            close(connection, oldAutoCommitStatus, ps, rs);
        }
    }

    public List<Map<String, Object>> queryMapList(BaseSQLInfo sql) {
        boolean oldAutoCommitStatus = false;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            oldAutoCommitStatus = connection.getAutoCommit();
            ps = connection.prepareStatement(sql.getSql());
            List<Object[]> argsParam = sql.getArgsParam();
            if (argsParam != null && argsParam.size() == 1) {
                Object[] ov = argsParam.get(0);
                for (int i = 0; i < ov.length; ) {
                    Object o = ov[i];
                    ps.setObject(++i, o);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<Map<String, Object>> result = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    row.put(columnLabel, rs.getObject(i));
                }
                result.add(row);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SqlParseException(e.getMessage());
        } finally {
            close(connection, oldAutoCommitStatus, ps, rs);
        }
    }

    @Override
    public int queryStream(BaseSQLInfo sql) {
        boolean oldAutoCommitStatus = false;
        Connection connection = null;
        AtomicInteger c = new AtomicInteger(0);
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
            Consumer<T> consumer = t -> {
                c.getAndIncrement();
                sql.getStreaming().accept(t);
            };
            switch (sql.getSqlType()) {
                case JOIN_SELECT:
                    ModelParseUtils.converterResultSet2ModelForJoinSelectStream(this.modelClass,
                            new BriefResultSetExecutor(ps.executeQuery()), consumer);
                    break;
                case NORMAL_SELECT:
                    ModelParseUtils.converterResultSet2ModelForNormalSelectStream(this.modelClass,
                            new BriefResultSetExecutor(ps.executeQuery()), consumer);
                    break;
                default:
                    throw new ParseResultSetException("sql type does not exist for streaming process");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SqlParseException(e.getMessage());
        } finally {
            closeConnection(connection, oldAutoCommitStatus);
        }
        return c.get();
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
