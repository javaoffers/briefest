package com.javaoffers.brief.modelhelper.fun.general;

import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.mapper.BaseMapper;
import com.javaoffers.brief.modelhelper.anno.derive.flag.IsDel;
import com.javaoffers.brief.modelhelper.anno.derive.flag.RowStatus;

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
     * @return  primary key id. or modify count num. so return void
     */
    public void saveOrModify(T model);

    /**
     * save or update.
     * By the @UniqueId field to query data, if the query not null then to update, or to insert.
     * @param model class
     * @return  primary key id. or modify count num. so return void
     */
    public void saveOrUpdate(T model);

    /**
     * save model
     * @param models class
     * @return primary key ids
     */
    public List<Id> saveBatch(Collection<T> models);

    /**
     * save or modify.
     * sql :  insert into on duplicate key update
     * @param models class
     * @return primary key id. or modify count num. so return void
     */
    public void saveOrModify(Collection<T> models);

    /**
     * save or update.
     * By the @UniqueId field to query data, if the query not null then to update, or to insert.
     * @param models class
     * @return  primary key id. or modify count num. so return void
     */
    public void saveOrUpdate(Collection<T> models);

    /**
     * delete model.Where conditions will be generated based on properties of the model
     * class for which there is a value.
     * Note that this is a physical deletion
     * @param model
     */
    public int remove(T model);

    /**
     * delete model by id
     * Note that this is a physical deletion
     */
    public int removeById(Serializable id );

    /**
     * delete model by ids
     * Note that this is a physical deletion
     */
    public int removeByIds(Serializable... ids );

    /**
     * delete model by ids
     * Note that this is a physical deletion
     */
    public <ID extends Serializable> int removeByIds(Collection<ID> ids);

    /**
     * logic delete model.Where conditions will be generated based on properties of the model
     * class for which there is a value.
     * {@link IsDel}
     * {@link RowStatus}
     * @param model
     */
    public int logicRemove(T model);

    /**
     * logic delete model by id
     * {@link IsDel}
     * {@link RowStatus}
     */
    public int logicRemoveById(Serializable id );

    /**
     * logic delete model by ids
     * {@link IsDel}
     * {@link RowStatus}
     */
    public int logicRemoveByIds(Serializable... ids );

    /**
     * logic delete model by ids
     * {@link IsDel}
     * {@link RowStatus}
     */
    public <ID extends Serializable> int logicRemoveByIds(Collection<ID> ids);

    /**
     * Update the model, note that the update condition is the property marked with the Unique annotation.
     * Only properties with values ​​are updated.
     * In other words, the @BaseUnique annotation will generate a Where condition, and other non-null properties will
     * generate a set statement. (will not update null to db)
     * 支持版本更新
     * @param model model
     * @return The number of bars affected by the update
     */
    public int modifyById(T model);

    /**
     * Update the model, note that the update condition is the property marked with the Unique annotation.
     * Only properties with values ​​are updated.
     * In other words, the @BaseUnique annotation will generate a Where condition, and the field will
     * generate a set statement. t. (will update null to db)
     * @param model model
     * @return The number of bars affected by the update
     */
    public int updateById(T model);

    /**
     * batch update. Empty fields will not be able to update the database.
     *  (will not update null to db)
     * @param models models
     * @return Affect the number of bars
     */
    public int modifyBatchById(Collection<T> models);

    /**
     * batch update ,Will update the database if the field is empty.
     *  (will update null to db)
     * @param models models
     * @return Affect the number of bars
     */
    public int updateBatchById(Collection<T> models);


    /**
     * Support version update.
     * Update the model, note that the update condition is the property marked with the Unique annotation.
     * Only properties with values ​​are updated.
     * In other words, the @BaseUnique annotation will generate a Where condition, and other non-null properties will
     * generate a set statement. (will not update null to db)
     * @param model model
     * @return The number of bars affected by the update
     */
    public int vsModifyById(T model);

    /**
     * Support version update.
     * Update the model, note that the update condition is the property marked with the Unique annotation.
     * Only properties with values ​​are updated.
     * In other words, the @BaseUnique annotation will generate a Where condition, and the field will
     * generate a set statement.  (will update null to db)
     * @param model model
     * @return The number of bars affected by the update
     */
    public int vsUpdateById(T model);

    /**
     * Support version update. (will not update null to db)
     * batch update. Empty fields will not be able to update the database.
     * @param models models
     * @return Affect the number of bars
     */
    public int vsModifyByIds(Collection<T> models);

    /**
     * Support version update. (will update null to db)
     * batch update ,Will update the database if the field is empty.
     * @param models models
     * @return Affect the number of bars
     */
    public int vsUpdateByIds(Collection<T> models);

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
     * @return return query result
     */
    public T queryOne(T model);

    /**
     * Query the main model, be careful not to include child models. Non-null properties will generate a where statement.
     * <>Note that properties such as Collection<Model> will be ignored, even if they are not null </>
     * <>if more than one throw error</>
     * @param model model
     * @return return query result
     */
    public T queryOnlyOne(T model);

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
     * @return not null
     */
    public Number count();

    /**
     * The number of statistical tables, through the specified field
     * @return not null
     */
    public Number count(C c);

    /**
     * The number of statistical tables, through the specified field
     * Statistical results after deduplication. count(DISTINCT c)
     * @return not null
     */
    public Number countDistinct(C c);


    /**
     * The number of statistical tables.  Will use the model as the where condition
     * @return not null
     */
    public Number count(T model);

    /**
     * The number of statistical tables, through the specified field.
     * Will use the model as the where condition
     * @return not null
     */
    public Number count(C c,T model);

    /**
     * The number of statistical tables, through the specified field
     * Statistical results after deduplication. count(DISTINCT c).
     * Will use the model as the where condition
     * @return not null
     */
    public Number countDistinct(C c,T model);

}
