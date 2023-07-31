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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: create by cmj on 2023/7/29 18:16
 */
public class BriefSaveExecutor implements SaveExecutor {

    DataSource dataSource;

    public BriefSaveExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Id save(BaseSQLInfo sql) {
        List<Id> ids = batchSave(sql);
        if(ids != null && ids.size() > 0){
            return ids.get(0);
        }
        return Id.EMPTY_ID;
    }

    @Override
    public List<Id> batchSave(BaseSQLInfo sql)  {
        Connection connection = null;
        Boolean oldAutoCommit = null;
        int batchSize = sql.getBatchSize();
        List<Id> ids = new ArrayList<>();
        try {
            connection = this.getConnection();
            String nativeSql = sql.getSql();
            List<Object[]> argsParam = sql.getArgsParam();
            oldAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(nativeSql);
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
                for (int i = 0, j = 1; i < size && j < size; i++, j++) {
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
                }
            } else {
                Object[] p = argsParam.get(0);
                for (int pi = 0; pi < p.length; ) {
                    Object ov = p[pi];
                    ps.setObject(++pi, ov);
                }
            }
            ps.executeBatch();
            //Avoid transactional inconsistencies
            if (oldAutoCommit) {
                connection.commit();
            }
            int i = 0;
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next() && i < batchSize) {
                Object object = rs.getObject(1);
                ids.add(Serializable2IdConvert.newId((Serializable) object));
                i++;
            }

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
           closeConnection(connection, oldAutoCommit);
        }
        return ids;
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
