package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.core.SQL;
import com.javaoffers.brief.modelhelper.exception.SqlParseException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
    public T query(SQL sql) {
        return null;
    }

    @Override
    public List<T> queryList(SQL sql) {
        try {
            Connection connection = this.dataSource.getConnection();
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
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] colNames = new String[columnCount];
            for(int i=0; i <= columnCount;){
                colNames[i] = metaData.getColumnName(++i);
            }

            while (rs.next()){


            }
        }catch (Exception e){
            e.printStackTrace();
            throw new SqlParseException(e.getMessage());
        }

        return null;
    }
}
