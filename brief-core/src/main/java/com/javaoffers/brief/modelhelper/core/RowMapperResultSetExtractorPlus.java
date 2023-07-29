
package com.javaoffers.brief.modelhelper.core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

/**
 * @author mingJie
 */
public class RowMapperResultSetExtractorPlus<T> implements ResultSetExtractor<List<T>> {

//    private RowMapper<T> rowMapper =  new BriefColumnMapRowMapper<T>();

	private Class modelClass;
    /**
     * Create a new RowMapperResultSetExtractor.
     */
    public RowMapperResultSetExtractorPlus(Class modelClass) {
    	this.modelClass = modelClass;
    }

    @Override
    public List<T> extractData(ResultSet rs) throws SQLException {
        List<T> results = new ArrayList<>();
        int rowNum = 0;
        while (rs.next()) {
            results.add(renderData(rs));
        }
        return results;
    }

	private T renderData(ResultSet rs) {

    	return null;
	}

//    static class BriefColumnMapRowMapper<T> implements RowMapper<T>  {
//        String[] names = null;
//        int columnCount = 0;
//		Map<String, T> result = new HashMap<>();
//        @Override
//        public T mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//
//
//
//            if (rowNum == 0) {
//                ResultSetMetaData rsmd = rs.getMetaData();
//                columnCount = rsmd.getColumnCount();
//                names = new String[columnCount];
//                for (int i = 1, j = 0; i <= columnCount; i++, j++) {
//                    String name = rsmd.getColumnLabel(i);
//                    names[j] = name;
//                    mapOfColumnValues.put(name, getColumnValue(rs, i));
//                }
//                return mapOfColumnValues;
//            } else {
////                            if(rowNum > 2){
////                                return new HashMap<>();
////                            }
//                for (int i = 1, j = 0; i <= columnCount; i++, j++) {
//                    String name = names[j];
//                    Object columnValue = rs.getObject(i);
//                    //mapOfColumnValues.put( names[j], rs.getObject(i));
//                }
//            }
//
//
//            return mapOfColumnValues;
//        }
//    }

}
