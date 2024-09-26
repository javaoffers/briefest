package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.convert.Serializable2IdConvert;
import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.core.SQL;
import com.javaoffers.brief.modelhelper.exception.SqlParseException;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: modify
 * @author: create by cmj on 2023/7/29 18:46
 */
public class BriefModifyExecutor implements ModifyExecutor {

    private DataSource dataSource;

    public BriefModifyExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int modify(BaseSQLInfo sql) {
        return batchModify(sql);
    }

    @Override
    public int batchModify(BaseSQLInfo sql) {
        Connection connection = null;
        PreparedStatement ps = null;
        Boolean oldAutoCommit = null;
        try {
            connection = this.getConnection();
            String nativeSql = sql.getSql();
            List<Object[]> argsParam = sql.getArgsParam();
            oldAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(nativeSql);
            int size = argsParam.size();
            int y = size % 2;
            if (size > 1) {
                if (y == 1) {
                    Object[] p = argsParam.remove(0);
                    for (int pi = 0; pi < p.length; ) {
                        Object ov = p[pi];
                        ps.setObject(++pi, ov);
                    }
                    ps.addBatch();
                }
                size = argsParam.size();
                for (int i = 0, j = 1; i < size && j < size; ) {
                    Object[] p = argsParam.get(i);
                    Object[] p2 = argsParam.get(j);
                    for (int pi = 0; pi < p.length; ) {
                        Object ov = p[pi];
                        ps.setObject(++pi, ov);
                    }
                    ps.addBatch();
                    for (int pi = 0; pi < p2.length; ) {
                        Object ov = p2[pi];
                        ps.setObject(++pi, ov);
                    }
                    ps.addBatch();
                    i+=2;
                    j+=2;
                }
            } else if(size == 1) {
                Object[] p = argsParam.get(0);
                for (int pi = 0; pi < p.length; ) {
                    Object ov = p[pi];
                    ps.setObject(++pi, ov);
                }
                ps.addBatch();
            }else{
                return 0;
            }
            int[] ints = ps.executeBatch();
            ps.clearBatch();
            if(oldAutoCommit){
                connection.commit();
                connection.setAutoCommit(oldAutoCommit);
            }
            int count  = 0;
            for(int i : ints){
                count = count + i;
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SqlParseException(e.getMessage());
        } finally {
            if (connection == null && oldAutoCommit != null) {
                try {
                    connection.setAutoCommit(oldAutoCommit);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new SqlParseException(e.getMessage());
                }
            }
            close(connection, oldAutoCommit, ps);
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
