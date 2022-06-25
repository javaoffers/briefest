package com.javaoffers.batis.modelhelper.mapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * create by cmj
 * @param <T>
 */
public interface ModelMapper<T> extends BaseMapper<T> {

    /**
     * 保存model
     * @param model 类
     * @return 主键id
     */
    public int save(T model);

    /**
     * 保存model
     * @param models 类
     * @return 主键id
     */
    public int saveBatch(List<T> models);

    /**
     * 删除model
     * @param model
     */
    public void remove(T model);

    /**
     * 删除model
     */
    public void removeById(Serializable id );

    /**
     * 删除model
     */
    public void removeById(Serializable... ids );

    /**
     * 删除model
     */
    public void removeById(Collection<Serializable> ids);

    /**
     * 更新model, 注意更新的条件为标有Unique注解的属性。 只更新有值的属性。
     * 换句话说就是 @Unique注解将会生成Where条件，其他非null的属性将会
     * 生成 set 语句
     * @param model model
     * @return 更新影响的条数
     */
    public int modify(T model);

    /**
     * 批处理更新
     * @param models models
     * @return 影响条数
     */
    public int modifyBatch(List<T> models);

    /**
     * 查询主model,注意不包含子model. 非null的属性将会 生成 where 语句.
     * <>注意会忽略Collection<Model> 这样的属性，及时他不为null </>
     * @param model model
     * @return 返回查询结果
     */
    public List<T> query(T model);

    /**
     * 根据id查询
     * @param id 主键id
     * @return model
     */
    public T queryById(Serializable id);

    /**
     * 根据id查询
     * @param ids 主键id
     * @return model
     */
    public List<T> queryByIds(Serializable... ids);

    /**
     * 根据id查询
     * @param ids 主键id
     * @return model
     */
    public List<T> queryByIds(List<Serializable> ids);

    /**
     * 查询根据Map<String,Object>. String: 表的字段名称。Object 字段对应的值
     * @param param
     * @return
     */
    public List<T> queryByColValue(Map<String,Object> param);


}
