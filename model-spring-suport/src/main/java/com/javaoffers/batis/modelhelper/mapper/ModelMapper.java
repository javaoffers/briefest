package com.javaoffers.batis.modelhelper.mapper;

import java.util.List;

/**
 * create by cmj
 * @param <T>
 */
public interface ModelMapper<T> extends BaseMapper<T> {

    /**
     * 保存主model
     * @param model 类
     * @return 主键id
     */
    public int saveMModel(T model);

    /**
     * 保存主model包括子model
     * @param model 类
     * @return 主键id
     */
    public int saveMModelAndSModel(T model);

    /**
     * 删除主model
     * @param model
     */
    public void deleteMModel(T model);

    /**
     * 删除model包括子model
     * @param model
     */
    public void delMModelAndSModel(T model);

    /**
     * 更新主model
     * @param model
     * @return
     */
    public int updateMModel(T model);

    /**
     * 更新主model和子model
     * @param model
     * @return
     */
    public int updateMModelAndSModel(T model);

    /**
     * 查询主model,注意不包含子model
     * @param model
     * @return 返回查询结果
     */
    public List<T> queryMModel(T model);


}
