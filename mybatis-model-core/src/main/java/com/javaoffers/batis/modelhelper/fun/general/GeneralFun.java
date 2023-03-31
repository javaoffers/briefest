package com.javaoffers.batis.modelhelper.fun.general;

import com.javaoffers.batis.modelhelper.anno.ColName;
import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.mapper.BaseMapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create by cmj
 * @param <T>
 */
public interface GeneralFun<T, C extends GetterFun<T, Object>, V> extends BaseMapper<T> {

    /**
     * save model
     * @param model class
     * @return primary key id
     */
    public Id save(T model);

    /**
     * save or modify.
     * sql :  insert into on duplicate key update
     * @param model class
     * @return primary key id
     */
    public List<Id> saveOrModify(T model);

    /**
     * save or replace
     * sql: replace into
     * @param model class
     * @return primary key id
     */
    public List<Id> saveOrReplace(T model);

    /**
     * save model
     * @param models class
     * @return primary key id
     */
    public List<Id> saveBatch(Collection<T> models);

    /**
     * save or modify.
     * sql :  insert into on duplicate key update
     * @param models class
     * @return primary key id
     */
    public List<Id> saveOrModify(Collection<T> models);

    /**
     * save or replace
     * sql: replace into
     * @param models class
     * @return primary key id
     */
    public List<Id> saveOrReplace(Collection<T> models);

    /**
     * delete model.Where conditions will be generated based on properties of the model
     * class for which there is a value.
     * @param model
     */
    public int remove(T model);

    /**
     * delete model by id
     */
    public int removeById(Serializable id );

    /**
     * delete model by ids
     */
    public int removeByIds(Serializable... ids );

    /**
     * delete model by ids
     */
    public <ID extends Serializable> int removeByIds(Collection<ID> ids);

    /**
     * Update the model, note that the update condition is the property marked with the Unique annotation.
     * Only properties with values ​​are updated.
     * In other words, the @BaseUnique annotation will generate a Where condition, and other non-null properties will
     * generate a set statement
     * @param model model
     * @return The number of bars affected by the update
     */
    public int modifyById(T model);

    /**
     * batch update
     * @param models models
     * @return Affect the number of bars
     */
    public int modifyBatchById(Collection<T> models);

    /**
     * Query the main model, be careful not to include child models. Non-null properties will generate a where statement.
     * <>Note that properties such as Collection<Model> will be ignored, even if they are not null </>
     * @param model model
     * @return return query result
     */
    public List<T> query(T model);

    /**
     * Query the main model, be careful not to include child models. Non-null properties will generate a where statement.
     * <>Note that properties such as Collection<Model> will be ignored, even if they are not null </>
     * @param model model
     * @param pageNum page number
     * @param pageSize Number of bars displayed per page
     * @return return query result
     */
    public List<T> query(T model,int pageNum,int pageSize);

    /**
     * Paging query full table data
     * @param pageNum page number, If the parameter is less than 1, it defaults to 1
     * @param pageSize Number of bars displayed per page， If the parameter is less than 1, it defaults to 10
     * @return return query result
     */
    public List<T> query(int pageNum,int pageSize);

    /**
     * query by id
     * @param id primary key id
     * @return model
     */
    public T queryById(Serializable id);

    /**
     * query by id
     * @param ids primary key id
     * @return model
     */
    public List<T> queryByIds(Serializable... ids);

    /**
     * query by id
     * @param ids primary key id
     * @return model
     */
    public <ID extends Serializable>  List<T> queryByIds(Collection<ID> ids);

    /**
     * query by id
     * @param ids primary key id
     * @return model
     */
    public <ID extends Serializable> List<T> queryByIds(List<ID> ids);

    /**
     * query by id
     * @param ids primary key id
     * @return model
     */
    public <ID extends Serializable> List<T> queryByIds(Set<ID> ids);


    /**
     * Map<String,Object>. String: Field names of the table. The value corresponding to the Object field
     * @param param Parameters. key database field name, value field value
     * @return model
     */
    public List<T> queryByParam(Map<String,Object> param);

    /**
     * Map<String,Object>. String: Field names of the table. The value corresponding to the Object field
     * @param param Parameters. key database field name, value field value
     * @param pageNum page number
     * @param pageSize Number of bars displayed per page
     * @return model
     */
    public List<T> queryByParam(Map<String,Object> param,int pageNum,int pageSize);

    /**
     * The number of statistical tables
     * @return
     */
    public long count();

    /**
     * The number of statistical tables, through the specified field
     * @return
     */
    public long count(C c);

    /**
     * The number of statistical tables, through the specified field
     * Statistical results after deduplication. count(DISTINCT c)
     * @return
     */
    public long countDistinct(C c);


    /**
     * The number of statistical tables
     * @return
     */
    public long count(T model);

    /**
     * The number of statistical tables, through the specified field
     * @return
     */
    public long count(C c,T model);

    /**
     * The number of statistical tables, through the specified field
     * Statistical results after deduplication. count(DISTINCT c)
     * @return
     */
    public long countDistinct(C c,T model);

}
