package com.javaoffers.batis.modelhelper.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

/**
 * create by cmj
 */
public class SQL implements BatchPreparedStatementSetter{

	private List<Object[]> argsParam;//封装参数,object[] 为一批参数，list.size 代表批次数
	private String sql;//要进行批处理的sql
	
	public SQL(String sql , List<Object[]> argsParam) {
		super();
		this.argsParam = argsParam;
		this.sql = sql;
	}

	public String getSql() {
		return sql;
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
						Object param = objects[l];
						if(param==null) {
							throw new NullPointerException("当前的批次参数中存在null 空指针");
						}
						ps.setObject(l+1, param);
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

}
