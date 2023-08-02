package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.core.SQL;
import com.javaoffers.brief.modelhelper.exception.ParseResultSetException;
import com.javaoffers.brief.modelhelper.exception.SqlParseException;
import com.javaoffers.brief.modelhelper.parse.ModelParseUtils;

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
        return null;
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
            if(argsParam != null && argsParam.size() == 1){
                Object[] ov = argsParam.get(0);
                for(int i=0; i<ov.length; ){
                    Object o = ov[i];
                    ps.setObject(++i, o);
                }
            }
            ResultSet rs = ps.executeQuery();
            switch (sql.getSqlType()){
                case JOIN_SELECT:
                    return ModelParseUtils.converterResultSet2ModelForJoinSelect(this.modelClass, new BriefResultSetExecutor(rs));
                case NORMAL_SELECT:
                    return ModelParseUtils.converterResultSet2ModelForNormalSelect(this.modelClass, new BriefResultSetExecutor(rs));
                default:
                    throw new ParseResultSetException("sql type does not exist");
            }


        }catch (Exception e){
            e.printStackTrace();
            throw new SqlParseException(e.getMessage());
        }finally {
            closeConnection(connection, oldAutoCommitStatus);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        }catch (Exception e){
            e.printStackTrace();
            throw new SqlParseException(e.getMessage());
        }

    }
}
