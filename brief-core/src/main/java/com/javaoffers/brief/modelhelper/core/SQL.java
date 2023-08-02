package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.utils.SQLType;

import java.util.List;
import java.util.Map;

/**
 * create by cmj
 */
public class SQL implements  BaseSQLInfo{
	final static String innerJoin = ConditionTag.INNER_JOIN.getTag().trim().toLowerCase();
	final static String leftJoin = ConditionTag.LEFT_JOIN.getTag().trim().toLowerCase();
	final static String rightJoin = ConditionTag.RIGHT_JOIN.getTag().trim().toLowerCase();
	private List<Object[]> argsParam;//封装参数,object[] 为一批参数，list.size 代表批次数
	private String sql;//要进行批处理的sql
	List<Map<String, Object>> paramMap; //原始值. 在执行sql前还又一次修改参数的机会。通过jql拦截器
	SQLType sqlType;
	public SQL(String sql , List<Object[]> argsParam) {
		super();
		this.argsParam = argsParam;
		this.sql = sql;
		String tmpSql = this.sql.toLowerCase();
		if(tmpSql.contains(innerJoin) || tmpSql.contains(leftJoin) || tmpSql.contains(rightJoin)) {
			sqlType = SQLType.JOIN_SELECT;
		} else {
			sqlType = SQLType.NORMAL_SELECT;
		}
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
	public SQLType getSqlType() {
		return sqlType;
	}

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
