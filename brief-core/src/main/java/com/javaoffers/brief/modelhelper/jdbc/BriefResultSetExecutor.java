package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.exception.ParseResultSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: create by cmj on 2023/7/30 15:55
 */
public class BriefResultSetExecutor implements ResultSetExecutor {

    static Logger logger = LoggerFactory.getLogger(BriefResultSetExecutor.class);

    private List<String> colNames = new ArrayList<>();

    private ResultSet resultSet;

    public BriefResultSetExecutor(ResultSet resultSet) {
        this.resultSet = resultSet;
        try {
            ResultSetMetaData metaData = resultSet.
                    getMetaData();
            int columnCount = metaData.getColumnCount();
            for(int i=0; i < columnCount;){
                colNames.add(metaData.getColumnLabel(++i));
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ParseResultSetException(e.getMessage());
        }
    }

    @Override
    public List<String> getColNames() {
        return this.colNames;
    }

    @Override
    public Object getColValueByColName(String colName) {
        try {
            return this.resultSet.getObject(colName);
        }catch (Exception e){
            //logger.warn("colName:{} result is null",colName);
            return null;
        }
    }

    public boolean nextRow(){
        try {
            return this.resultSet.next();
        }catch (Exception e){
            e.printStackTrace();
            throw new ParseResultSetException(e.getMessage());
        }

    }
}
