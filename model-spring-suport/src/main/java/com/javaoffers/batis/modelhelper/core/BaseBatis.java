package com.javaoffers.batis.modelhelper.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description: 该项目的顶级接口
 * @param <T> model
 * @param <ID> 主键
 * @Auther: create by cmj on 2022/05/22 02:49
 */
public interface BaseBatis<T,ID>{
	
    /*****************************************************************************/ 
	public int saveData(String sql);

	public int saveData(String sql,Map<String,Object> map);
	
	public int deleteData(String sql);

	public int deleteData(String sql,Map<String,Object> map);
	
	public int updateData(String sql);

	public int updateData(String sql,Map<String,Object> map);
	
	public List<Map<String,Object>> queryData(String sql);

	public List<Map<String,Object>> queryData(String sql,Map<String,Object> map);
	
	/*****************************************************************************/ 
	public <E> List<E> queryDataForT(String sql,Class<E> clazz);

	public <E> List<E> queryDataForT4(String sql,Map<String,Object> map,Class<E> clazz);

	/*****************************************************************************/ 
	public Integer batchUpdate(String sql,List<Map<String,Object>> paramMap);

	public List<Serializable> batchInsert(String sql, List<Map<String,Object>> paramMap);

	/*****************************************************************************/ 

}
