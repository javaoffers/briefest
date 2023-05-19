package com.javaoffers.batis.modelhelper.fun.general.impl;

import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.exception.GetColValueException;
import com.javaoffers.batis.modelhelper.exception.ParseParamException;
import com.javaoffers.batis.modelhelper.fun.GetterFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereModifyFun;
import com.javaoffers.batis.modelhelper.fun.crud.WhereSelectFun;
import com.javaoffers.batis.modelhelper.fun.crud.delete.DeleteWhereFun;
import com.javaoffers.batis.modelhelper.fun.crud.impl.SelectFunImpl;
import com.javaoffers.batis.modelhelper.fun.crud.impl.delete.DeleteFunImpl;
import com.javaoffers.batis.modelhelper.fun.crud.impl.insert.InsertFunImpl;
import com.javaoffers.batis.modelhelper.fun.crud.impl.update.UpdateFunImpl;
import com.javaoffers.batis.modelhelper.fun.crud.update.SmartUpdateFun;
import com.javaoffers.batis.modelhelper.fun.general.GeneralFun;
import com.javaoffers.batis.modelhelper.utils.BlurUtils;
import com.javaoffers.batis.modelhelper.utils.ColumnInfo;
import com.javaoffers.batis.modelhelper.utils.Lists;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import com.javaoffers.batis.modelhelper.utils.TableInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Commonly used function implementation
 *
 * @param <T>
 * @author create by mingjie
 */
public class GeneralFunImpl<T, C extends GetterFun<T, Object>, V> implements GeneralFun<T, C, V> {

    static ConvertRegisterSelectorDelegate convert = ConvertRegisterSelectorDelegate.convert;

    private AtomicInteger ato = new AtomicInteger(0);

    private Class<T> mClass;

    private SelectFunImpl<T> selectFun;

    private InsertFunImpl<T> insertFun;

    private UpdateFunImpl<T, C, V> updateFun;

    private DeleteFunImpl<T> deleteFun;

    private String tableName;

    private String primaryColNmae;

    private Field primaryField;

    public GeneralFunImpl(Class<T> mClass, SelectFunImpl<T> selectFun,
                          InsertFunImpl<T> insertFun, UpdateFunImpl<T, C, V> updateFun,
                          DeleteFunImpl<T> deleteFun) {
        this.mClass = mClass;
        this.selectFun = selectFun;
        this.insertFun = insertFun;
        this.updateFun = updateFun;
        this.deleteFun = deleteFun;
        this.tableName = TableHelper.getTableName(mClass);
        TableInfo tableInfo = TableHelper.getTableInfo(mClass);
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        primaryColNmae = primaryColNames.keySet().iterator().next();
        primaryField = tableInfo.getColNameAndFieldOfModel().get(primaryColNmae).get(0);
    }

    @Override
    public Id save(T model) {
        if (model == null) {
            return Id.EMPTY_ID;
        }
        Id ex = insertFun.colAll(model).ex();
        if (ex != null) {
            return ex;
        }
        return Id.EMPTY_ID;
    }

    @Override
    public void saveOrModify(T model) {
        if (model == null) {
            return;
        }
        ArrayList<T> models = new ArrayList<>();
        models.add(model);
        saveOrModify(models);
    }

    @Override
    public void saveOrUpdate(T model) {
        if (model == null) {
            return;
        }
        this.saveOrUpdate(Lists.newArrayList(model));
    }

    @Override
    public void saveOrReplace(T model) {
        if (model == null) {
            return;
        }
        ArrayList<T> models = new ArrayList<>();
        models.add(model);
        this.saveOrModify(models);
    }

    @Override
    public List<Id> saveBatch(Collection<T> models) {
        if (CollectionUtils.isEmpty(models)) {
            return Collections.EMPTY_LIST;
        }
        List<Id> exs = insertFun.colAll(models).exs();
        if (exs != null) {
            return exs;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void saveOrModify(Collection<T> models) {
        if (CollectionUtils.isEmpty(models)) {
            return;
        }
        insertFun.colAll(models).dupUpdate().exs();
    }

    @Override
    public void saveOrUpdate(Collection<T> models) {

        if (CollectionUtils.isEmpty(models)) {
            return;
        }
        //copy
        models = new ArrayList<>(models);
        TableInfo tableInfo = TableHelper.getTableInfo(mClass);
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        if (MapUtils.isEmpty(primaryColNames)) {
            return;
        }
        AtomicBoolean status = new AtomicBoolean(false);
        WhereSelectFun<T, Object> where = this.selectFun.col(primaryColNmae + " as " + primaryField.getName()).where();
        Map<String, List<Field>> originalColNameOfModelField = tableInfo.getOriginalColNameOfModelField();
        Map<String, T> uniqueKeyMap = new HashMap<>();
        List<Object> uniqueKeyValues = new ArrayList<>();
        for (T model : models) {
            primaryColNames.keySet().forEach(uniqueIdColName -> {
                List<Field> uniqueIdField = originalColNameOfModelField.get(uniqueIdColName);
                List<Object> values = uniqueIdField.stream().map(field -> {
                    try {
                        return field.get(model);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new ParseParamException(e.getMessage());
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());
                values.forEach(uniqueKey -> {
                    uniqueKeyMap.put(uniqueKey.toString(), model);
                });
                uniqueKeyValues.addAll(values);
            });
        }

        if (CollectionUtils.isNotEmpty(uniqueKeyValues)) {
            HashMap<String, Object> param = new HashMap<>();
            String newColNameTag = getNewColNameTag();
            param.put(newColNameTag, uniqueKeyValues);
            where.condSQL(primaryColNmae + " in ( #{" + newColNameTag + "} )", param);
            status.set(true);
        }

        List<T> needUpdate = new ArrayList<>();
        //Divide data into update/insert
        if (status.get()) {
            //query by unique ids
            List<T> exs = where.exs();
            exs.forEach(model -> {
                try {
                    Object o = primaryField.get(model);
                    T m = uniqueKeyMap.get(o.toString());
                    if (m != null) {
                        needUpdate.add(m);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new ParseParamException(e.getMessage());
                }
            });
            // need save
            models.removeAll(needUpdate);
        }
        this.saveBatch(models);
        this.updateBatchById(needUpdate);
    }

    @Override
    public void saveOrReplace(Collection<T> models) {
        if (CollectionUtils.isEmpty(models)) {
            return;
        }
        insertFun.colAll(models).dupReplace().exs();
    }

    @Override
    public int remove(T model) {
        if (model == null) {
            return 0;
        }
        DeleteWhereFun<T, GetterFun<T, Object>, Object> where = deleteFun.where();
        AtomicBoolean status = parseWhere(model, where);
        if (status.get()) {
            return where.ex();
        }
        return 0;
    }

    @Override
    public int removeById(Serializable id) {
        return removeByIds(id);
    }

    @Override
    public int removeByIds(Serializable... ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        Set<Serializable> idSet = Arrays.stream(ids).collect(Collectors.toSet());
        return removeByIds(idSet);
    }

    @Override
    public int removeByIds(Collection ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        TableInfo tableInfo = getTableInfo(mClass);
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        DeleteWhereFun<T, GetterFun<T, Object>, Object> where = deleteFun.where();
        AtomicBoolean status = new AtomicBoolean(false);
        primaryColNames.forEach((colName, colInfo) -> {
            if (!status.get()) {
                status.set(true);
                Map<String, Object> param = new HashMap<>();
                String newColNameTag = getNewColNameTag();
                param.putIfAbsent(newColNameTag, ids);
                where.condSQL(colName + " in ( #{" + newColNameTag + "} ) ", param);
            }
        });
        if (status.get()) {
            return where.ex();
        }
        return 0;
    }

    @Override
    public int modifyById(T model) {
        if (model != null) {
            return modifyBatchById(Lists.newArrayList(model));
        }
        return 0;
    }

    @Override
    public int updateById(T model) {
        if (model == null) {
            return 0;
        }
        return renovateBatchById(Lists.newArrayList(model), true);
    }

    @Override
    public int modifyBatchById(Collection<T> models) {
        return renovateBatchById(models, false);
    }

    @Override
    public int updateBatchById(Collection<T> models) {
        return renovateBatchById(models, true);
    }

    @Override
    public List<T> query(T model) {
        if (model == null) {
            return Collections.EMPTY_LIST;
        }
        Pair<Boolean, WhereSelectFun<T, Object>> whereInfo = parseQueryWhere(model);
        //If there is no condition to return directly, the full table will not be queried. Avoid full table scan
        if (!whereInfo.getLeft()) {
            return Collections.EMPTY_LIST;
        }
        WhereSelectFun<T, Object> where = whereInfo.getRight();
        return where.exs();
    }

    @Override
    public List<T> query(T model, int pageNum, int pageSize) {
        if (model == null) {
            return Collections.EMPTY_LIST;
        }
        Pair<Boolean, WhereSelectFun<T, Object>> whereInfo = parseQueryWhere(model);
        //If there is no condition to return directly, the full table will not be queried. Avoid full table scan
        if (!whereInfo.getLeft()) {
            return Collections.EMPTY_LIST;
        }
        WhereSelectFun<T, Object> where = whereInfo.getRight();
        where.limitPage(pageNum, pageSize);
        return where.exs();
    }

    @Override
    public List<T> query(int pageNum, int pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        List<T> exs = this.selectFun.colAll().where().limitPage(pageNum, pageSize).exs();
        if (exs == null) {
            exs = Collections.EMPTY_LIST;
        }
        return exs;
    }

    @Override
    public T queryById(Serializable id) {
        if (id == null) {
            return null;
        }
        List<T> ts = queryByIds(id);
        if (ts == null || ts.size() == 0) {
            return null;
        }
        return ts.get(0);
    }

    @Override
    public List<T> queryByIds(Serializable... ids) {
        if (ids == null || ids.length == 0) {
            return Collections.EMPTY_LIST;
        }
        Set idSet = Arrays.stream(ids).collect(Collectors.toSet());
        return queryByIds(idSet);
    }

    @Override
    public List<T> queryByIds(Collection ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        WhereSelectFun<T, Object> where = this.selectFun.colAll().where();
        Map<String, ColumnInfo> primaryColNames = TableHelper.getTableInfo(mClass).getPrimaryColNames();
        AtomicBoolean status = new AtomicBoolean(false);
        primaryColNames.forEach((colName, colInfo) -> {
            if (!status.get()) {
                HashMap<String, Object> param = new HashMap<>();
                String newColNameTag = getNewColNameTag();
                param.put(newColNameTag, ids);
                status.set(true);
                where.condSQL(colName + " in ( #{" + newColNameTag + "} ) ", param);
            }
        });
        if (status.get()) {
            List<T> exs = where.exs();
            if (exs != null && exs.size() > 0) {
                return exs;
            }
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<T> queryByIds(List ids) {
        return queryByIds((Collection) ids);
    }

    @Override
    public List<T> queryByIds(Set ids) {
        return queryByIds((Collection) ids);
    }

    @Override
    public List<T> queryByParam(Map<String, Object> param) {
        //avoid all table scan
        if (MapUtils.isEmpty(param)) {
            return Collections.EMPTY_LIST;
        }
        return queryByParam(param, -1, -1, false);
    }

    @Override
    public List<T> queryByParam(Map<String, Object> param, int pageNum, int pageSize) {
        return queryByParam(param, pageNum, pageSize, true);
    }

    @Override
    public Number count() {
        T ex = selectFun.col("count(1) as " + primaryField.getName()).where().ex();
        return getNumber(primaryField, ex);
    }

    @Override
    public Number count(C c) {
        Assert.isTrue(c != null, " count is null .");
        Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(c);
        T ex = selectFun.col("count(" + colNameAndAliasName.getLeft() + ") as " + primaryField.getName()).where().ex();
        return getNumber(primaryField, ex);
    }

    @Override
    public Number countDistinct(C c) {
        Assert.isTrue(c != null, " count is null .");
        Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(c);
        T ex = selectFun.col("count(Distinct(" + colNameAndAliasName.getLeft() + ")) as " + primaryField.getName()).where().ex();
        return getNumber(primaryField, ex);
    }

    @Override
    public Number count(T model) {
        WhereSelectFun<T, Object> where = selectFun.col("count(1) as " + primaryField.getName()).where();
        if (parseWhere(model, where).get()) {
            T ex = where.ex();
            return getNumber(primaryField, ex);
        }
        return 0L;
    }

    @Override
    public Number count(C c, T model) {
        Assert.isTrue(c != null, " count is null .");
        Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(c);
        WhereSelectFun<T, Object> where = selectFun.col("count(" + colNameAndAliasName.getLeft() + ") as " + primaryField.getName()).where();
        if (parseWhere(model, where).get()) {
            T ex = where.ex();
            return getNumber(primaryField, ex);
        }
        return 0L;
    }

    @Override
    public Number countDistinct(C c, T model) {
        Assert.isTrue(c != null, " count is null .");
        Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(c);
        WhereSelectFun<T, Object> where = selectFun.col("count(Distinct(" + colNameAndAliasName.getLeft() + ")) as " + primaryField.getName()).where();
        if (parseWhere(model, where).get()) {
            T ex = where.ex();
            return getNumber(primaryField, ex);
        }
        return 0L;
    }

    private Pair<Boolean, WhereSelectFun<T, Object>> parseQueryWhere(T model) {
        WhereSelectFun<T, Object> where = this.selectFun.colAll().where();
        AtomicBoolean isExecute = parseWhere(model, where);
        return Pair.of(isExecute.get(), where);
    }

    private List<T> queryByParam(Map<String, Object> param, int pageNum, int pageSize, boolean isPage) {
        if (param != null && param.size() > 0) {
            WhereSelectFun<T, Object> where = selectFun.colAll().where();
            AtomicBoolean status = new AtomicBoolean(false);
            param.forEach((colName, value) -> {
                Map<String, Object> param_ = new HashMap<>();
                String newColNameTag = getNewColNameTag();
                param_.put(newColNameTag, value);
                where.condSQL(colName + " in ( #{" + newColNameTag + "} ) ", param_);
                status.set(true);
            });
            if (status.get()) {
                if (isPage) {
                    where.limitPage(pageNum, pageSize);
                }
                List<T> exs = where.exs();
                if (exs != null) {
                    return exs;
                }
            }
        }
        return Collections.EMPTY_LIST;
    }

    private String getNewColNameTag() {
        return ato.getAndIncrement() + "_G_";
    }

    private TableInfo getTableInfo(Class modelClass) {
        TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
        Assert.isTrue(tableInfo != null, "The table information corresponding to this class cannot be queried");
        return tableInfo;
    }

    private AtomicBoolean parseWhere(T model, WhereFun where) {
        TableInfo tableInfo = getTableInfo(model.getClass());
        Map<String, ColumnInfo> originalColNames = tableInfo.getColNames();
        Map<String, List<Field>> colNameOfModelField = tableInfo.getColNameAndFieldOfModel();
        AtomicBoolean status = new AtomicBoolean(false);

        if (colNameOfModelField != null && colNameOfModelField.size() > 0) {
            colNameOfModelField.forEach((colName, fields) -> {
                Object o = null;
                try {
                    for (int i = 0; fields != null && i < fields.size(); i++) {
                        Field field = fields.get(i);
                        if (BlurUtils.containsBlurAnno(field)) {
                            continue;
                        }
                        o = field.get(model);
                        if (o != null) {
                            if (originalColNames.get(colName) != null
                                    && originalColNames.get(colName).isAutoincrement()
                                    && o instanceof Number
                                    && ((Number) o).longValue() == 0L) {
                                return;
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (o != null) {
                    HashMap<String, Object> param = new HashMap<>();
                    String newColNameTag = getNewColNameTag();
                    param.putIfAbsent(newColNameTag, o);
                    where.condSQL(colName + " in ( #{" + newColNameTag + "} ) ", param);
                    status.set(true);
                }
            });
        }
        return status;
    }

    private void parseWhereById(WhereModifyFun<T, V> where, AtomicBoolean status, T model) {
        TableInfo tableInfo = TableHelper.getTableInfo(mClass);
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        Map<String, List<Field>> colNameOfModelField = tableInfo.getColNameAndFieldOfModel();
        WhereModifyFun<T, V> finalWhere = where;
        primaryColNames.forEach((colName, colInfo) -> {
            List<Field> fields = colNameOfModelField.get(colName);
            Object o = null;
            for (Field field : fields) {
                try {
                    if (BlurUtils.containsBlurAnno(field)) {
                        continue;
                    }
                    o = field.get(model);
                    if (o != null) {
                        if (o instanceof Number && colInfo.isAutoincrement() && ((Number) o).longValue() == 0L) {
                            continue;
                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new GetColValueException(e.getMessage());
                }
            }
            if (o != null) {
                Map<String, Object> param = new HashMap<>();
                String newColNameTag = getNewColNameTag();
                param.put(newColNameTag, o);
                finalWhere.condSQL(colName + " in ( #{" + newColNameTag + "} ) ", param);
                status.set(true);
            }
        });
    }

    private long getNumber(Field field, T ex) {
        if (ex == null) {
            return 0;
        }
        Object o = null;
        try {
            o = field.get(ex);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
        if (o instanceof Number) {
            return ((Number) o).longValue();
        } else {
            return Long.parseLong(o.toString());
        }
    }

    private int renovateBatchById(Collection<T> models, boolean updateNull) {
        if (models == null || models.size() == 0) {
            return 0;
        }
        models = models.stream().filter(Objects::nonNull).collect(Collectors.toList());
        WhereModifyFun<T, V> where = null;
        AtomicBoolean status = new AtomicBoolean(false);
        SmartUpdateFun<T, C, V> npdateNull = updateNull ? this.updateFun.updateNull() : this.updateFun.npdateNull();
        int i = 0;
        for (T model : models) {
            if (i == 0) {
                where = npdateNull.colAll(model).where();
            } else {
                where.addBatch().colAll(model).where();
                //Where participation should be consistent otherwise it will affect batch processing
                ato = new AtomicInteger(0);
            }
            i++;
            AtomicBoolean status_ = new AtomicBoolean(false);
            parseWhereById(where, status_, model);
            if (!status_.get()) {
                //This statement should be ignored and should not execute successfully
                where.condSQL(" 1 = 2 ");
            } else {
                status.set(true);
            }
        }
        if (status.get()) {
            return where.ex().intValue();
        }
        return 0;
    }

}
