package com.javaoffers.brief.modelhelper.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

/**
 * create by cmj
 */
public class SQL implements BatchPreparedStatementSetter, BaseSQLInfo{

	private List<Object[]> argsParam;//封装参数,object[] 为一批参数，list.size 代表批次数
	private String sql;//要进行批处理的sql
	List<Map<String, Object>> paramMap; //原始值. 在执行sql前还又一次修改参数的机会。通过jql拦截器
	
	public SQL(String sql , List<Object[]> argsParam) {
		super();
		this.argsParam = argsParam;
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	@Override
	public List<Map<String, Object>> getParams() {
		return this.paramMap;
	}

	@Override
	public void resetSql(String sql) {
		this.sql = sql;
	}

	public List<Object[]> getArgsParam() {
		return argsParam;
	}

	@Override
	public void setValues(PreparedStatement ps, int i) throws SQLException {
		
		if(argsParam!=null&& argsParam.size()>0&&StringUtils.isNoneBlank(sql)) {
				Object[] objects = argsParam.get(i);
				if(objects!=null&&objects.length>0) {
					for(int l=0;l<objects.length;l++) {
						Object object = objects[l];
						ps.setObject(l+1, object);
					}
				}
		}
		
	}

	@Override
	public int getBatchSize() {
		if(argsParam!=null) {
			return argsParam.size();
		}
		return 0;
	}

	public void setParamMap(List<Map<String, Object>> paramMap) {
		this.paramMap = paramMap;
	}
}
