package com.javaoffers.batis.modelhelper.core;

import com.javaoffers.batis.modelhelper.parse.ModelParseUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**@Description: 核心实现类
 * @Auther: create by cmj on 2022/05/22 02:56
 */
public class BaseBatisImpl<T, ID> implements BaseBatis<T, ID> {

	public static BaseBatisImpl baseBatis = new BaseBatisImpl(null);

	private JdbcTemplate jdbcTemplate;

	public BaseBatisImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public synchronized void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		if(this.jdbcTemplate == null){
			this.jdbcTemplate = jdbcTemplate;
		}
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
		List<Map<String, Object>> queryForList = this.jdbcTemplate.queryForList(sql);
		return queryForList;
	}

	@Override
	public List<Map<String, Object>> queryData(String sql, Map<String, Object> map) {
		SQL batchSQL = SQLParse.getSQL(sql, map);
		 //query(sql, args, getColumnMapRowMapper());
		List<Map<String, Object>> result = this.jdbcTemplate.query(batchSQL.getSql(), batchSQL.getArgsParam().get(0),new ColumnMapRowMapper());
		return result;
	}

	/*********************************支持Model*********************************/
	public <E> List<E> queryDataForT(String sql, Class<E> clazz) {
		List<Map<String, Object>> list_map = this.jdbcTemplate.queryForList(sql);
		ArrayList<E> list = ModelParseUtils.converterMap2Model(clazz, list_map);
		return list;
	}

	@Override
	public <E> List<E> queryDataForT4(String sql, Map<String, Object> paramMap, Class<E> clazz) {
		List<Map<String, Object>> maps = queryData(sql, paramMap);
		return ModelParseUtils.converterMap2Model(clazz, maps);
	}

	/*********************************批处理*********************************/
	public Integer batchUpdate(String sql,List<Map<String,Object>> paramMap ) {
		SQL batchSQL = SQLParse.parseSqlParams(sql, paramMap);
		int[] is = this.jdbcTemplate.batchUpdate( batchSQL.getSql(), batchSQL);
		return Integer.valueOf(is.length);
	}

}
