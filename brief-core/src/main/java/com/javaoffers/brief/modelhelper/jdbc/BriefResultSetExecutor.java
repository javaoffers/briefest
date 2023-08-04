package com.javaoffers.brief.modelhelper.jdbc;

import com.javaoffers.brief.modelhelper.exception.ParseResultSetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @description:
 * @author: create by cmj on 2023/7/30 15:55
 */
public class BriefResultSetExecutor implements ResultSetExecutor {

    static Logger logger = LoggerFactory.getLogger(BriefResultSetExecutor.class);

    private List<String> colNames = new ArrayList<>();

    private Map<String, Boolean> colNameBool = new HashMap<>();

    private ResultSet resultSet;

    public BriefResultSetExecutor(ResultSet resultSet) {
        this.resultSet = resultSet;
        try {
            ResultSetMetaData metaData = resultSet.
                    getMetaData();
            int columnCount = metaData.getColumnCount();
            for(int i=0; i < columnCount;){
                String columnLabel = metaData.getColumnLabel(++i);
                colNames.add(columnLabel);
                colNameBool.put(columnLabel, true);
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
            if(colNameBool.containsKey(colName)){
                return this.resultSet.getObject(colName);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.warn("colName:{} result is null",colName);
        }
        return null;
    }

    @Override
    public Object getColValueByColPosition(int position) {
        try {
            return this.resultSet.getObject(position);
        }catch (Exception e){
            e.printStackTrace();
            logger.warn("colName:{} result is null",position);
        }
        return null;
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
