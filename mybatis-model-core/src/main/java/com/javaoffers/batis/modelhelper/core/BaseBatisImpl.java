package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.fun.JdbcTemplateCondition;
import com.javaoffers.batis.modelhelper.parse.ModelParseUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.InterruptibleBatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**@Description: core implementation class
 * @Auther: create by cmj on 2022/05/22 02:56
 */
public class BaseBatisImpl<T, ID> implements BaseBatis<T, ID> {

	private static final Object[] EMPTY = new Object[0];

	private JdbcTemplate jdbcTemplate;

	public static  <T, ID> BaseBatisImpl getInstance(JdbcTemplateCondition jdbcTemplate) {
		return new BaseBatisImpl<T,ID>(jdbcTemplate.getTemplate());
	}

	private BaseBatisImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/****************************crud****************************/
	public int saveData(String sql) {
		return saveData(sql,Collections.EMPTY_MAP);
	}

	@Override
	public int saveData(String sql, Map<String, Object> map) {
		SQL sql_ = SQLParse.getSQL(sql, map);
		return this.jdbcTemplate.update(sql_.getSql(),  new ArgumentPreparedStatementSetter(sql_.getArgsParam().get(0)));
	}

	public int deleteData(String sql) {
		return deleteData(sql,Collections.EMPTY_MAP);
	}

	@Override
	public int deleteData(String sql, Map<String, Object> map) {
		SQL sql_ = SQLParse.getSQL(sql, map);
		return this.jdbcTemplate.update(sql_.getSql(),  new ArgumentPreparedStatementSetter(sql_.getArgsParam().get(0)));
	}

	public int updateData(String sql) {
		return updateData(sql, Collections.EMPTY_MAP);
	}

	@Override
	public int updateData(String sql, Map<String, Object> map) {
		SQL sql_ = SQLParse.getSQL(sql, map);
		return this.jdbcTemplate.update(sql_.getSql(),  new ArgumentPreparedStatementSetter(sql_.getArgsParam().get(0)));
	}

	public List<Map<String, Object>> queryData(String sql) {
		List<Map<String, Object>> queryForList = queryData(sql, EMPTY);
		return queryForList;
	}

	@Override
	public List<Map<String, Object>> queryData(String sql, Map<String, Object> map) {
		SQL batchSQL = SQLParse.getSQL(sql, map);
		 //query(sql, args, getColumnMapRowMapper());
		List<Map<String, Object>> result = queryData(batchSQL.getSql(), batchSQL.getArgsParam().get(0));
		return result;
	}

	/*********************************Support Model*********************************/
	public <E> List<E> queryDataForT(String sql, Class<E> clazz) {
		List<Map<String, Object>> list_map = queryData(sql, EMPTY);
		List<E> list = ModelParseUtils.converterMap2Model(clazz, list_map);
		return list;
	}

	@Override
	public <E> List<E> queryDataForT4(String sql, Map<String, Object> paramMap, Class<E> clazz) {
		List<Map<String, Object>> maps = queryData(sql, paramMap);
		return ModelParseUtils.converterMap2Model(clazz, maps);
	}

	/*********************************batch processing*********************************/
	public Integer batchUpdate(String sql,List<Map<String,Object>> paramMap ) {
		SQL batchSQL = SQLParse.parseSqlParams(sql, paramMap);
		int[] is = this.jdbcTemplate.batchUpdate( batchSQL.getSql(), batchSQL);
		Assert.isTrue(is !=null ," batch update is null ");
		AtomicInteger countSuccess = new AtomicInteger();
		Arrays.stream(is).forEach(countSuccess::addAndGet);
		return Integer.valueOf(countSuccess.get());
	}

	@Override
	public List<Serializable> batchInsert(String sql, List<Map<String, Object>> paramMap) {

		SQL pss = SQLParse.parseSqlParams(sql, paramMap);
		LinkedList<Serializable> ids = new LinkedList<>();
		jdbcTemplate.execute(new InsertPreparedStatementCreator(pss.getSql()), (PreparedStatementCallback<List<Serializable>>) ps -> {
			try {
				int batchSize = pss.getBatchSize();
				InterruptibleBatchPreparedStatementSetter ipss =
						(pss instanceof InterruptibleBatchPreparedStatementSetter ?
								(InterruptibleBatchPreparedStatementSetter) pss : null);
				if (JdbcUtils.supportsBatchUpdates(ps.getConnection())) {
					for (int i = 0; i < batchSize; i++) {
						pss.setValues(ps, i);
						if (ipss != null && ipss.isBatchExhausted(i)) {
							break;
						}
						ps.addBatch();
					}
					ps.executeBatch();

				}
				else {
					List<Integer> rowsAffected = new ArrayList<>();
					for (int i = 0; i < batchSize; i++) {
						pss.setValues(ps, i);
						if (ipss != null && ipss.isBatchExhausted(i)) {
							break;
						}
						rowsAffected.add(ps.executeUpdate());
					}
					int[] rowsAffectedArray = new int[rowsAffected.size()];
					for (int i = 0; i < rowsAffectedArray.length; i++) {
						rowsAffectedArray[i] = rowsAffected.get(i);
					}

				}

				int i = 0;
				ResultSet rs = ps.getGeneratedKeys() ;
				while(rs.next() && i < batchSize){
					Object object = rs.getObject(1);
					ids.add(new IdImpl((Serializable) object)) ;
					i++ ;
				}
				return ids;
			}
			finally {
				if (pss instanceof ParameterDisposer) {
					((ParameterDisposer) pss).cleanupParameters();
				}
			}
		});

		return ids;
	}

	/**
	 * Basic query implementation
	 * @param nativeSql sql
	 * @param param parameter
	 * @return
	 */
	private List<Map<String, Object>> queryData(String nativeSql,Object[] param){
		return this.jdbcTemplate.query(nativeSql,param,new ColumnMapRowMapper(){
			@Override
			public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				Map<String, Object> mapOfColumnValues = createColumnMap(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					ColumnInfo columnInfo = lookupColumnName(rsmd, i);
					mapOfColumnValues.putIfAbsent(columnInfo.getTableName() + getColumnKey(columnInfo.getColName()), getColumnValue(rs, i));
				}
				return mapOfColumnValues;
			}

			public  ColumnInfo lookupColumnName(ResultSetMetaData resultSetMetaData, int columnIndex) throws SQLException {
				String name = resultSetMetaData.getColumnLabel(columnIndex);
				if (!StringUtils.hasLength(name)) {
					name = resultSetMetaData.getColumnName(columnIndex);
				}
				String tableName = resultSetMetaData.getTableName(columnIndex);
				return  new ColumnInfo(name, tableName);
			}

			class ColumnInfo{

				private String colName;
				private String tableName;

				public ColumnInfo(String colName, String tableName) {
					this.colName = colName;
					this.tableName = tableName;
				}

				public String getColName() {
					return colName;
				}

				public void setColName(String colName) {
					this.colName = colName;
				}

				public String getTableName() {
					if(org.apache.commons.lang3.StringUtils.isBlank(this.tableName)){
						return "";
					}
					return tableName + "__";
				}

				public void setTableName(String tableName) {
					this.tableName = tableName;
				}
			}
		});
	}

}
