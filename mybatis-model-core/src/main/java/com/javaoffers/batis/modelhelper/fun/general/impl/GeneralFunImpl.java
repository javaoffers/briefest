package com.javaoffers.batis.modelhelper.fun.general.impl;

import com.javaoffers.batis.modelhelper.core.Id;
import com.javaoffers.batis.modelhelper.exception.GetColValueException;
import com.javaoffers.batis.modelhelper.fun.AggTag;
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
import com.javaoffers.batis.modelhelper.utils.ColumnInfo;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import com.javaoffers.batis.modelhelper.utils.TableInfo;
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
 * @author create by mingjie
 * @param <T>
 */
public class GeneralFunImpl<T, C extends GetterFun<T, Object>,V> implements GeneralFun<T,C,V> {

    private AtomicInteger ato = new AtomicInteger(0);

    private Class<T> mClass;

    private SelectFunImpl<T> selectFun;

    private InsertFunImpl<T> insertFun;

    private UpdateFunImpl<T,C,V> updateFun;

    private DeleteFunImpl<T> deleteFun;

    public GeneralFunImpl(Class<T> mClass, SelectFunImpl<T> selectFun, InsertFunImpl<T> insertFun, UpdateFunImpl<T, C, V> updateFun, DeleteFunImpl<T> deleteFun) {
        this.mClass = mClass;
        this.selectFun = selectFun;
        this.insertFun = insertFun;
        this.updateFun = updateFun;
        this.deleteFun = deleteFun;
    }

    @Override
    public long save(T model) {
        Id ex = insertFun.colAll(model).ex();
        if(ex != null){
            return ex.toLong();
        }
        return 0L;
    }

    @Override
    public List<Long> saveBatch(Collection<T> models) {
        List<Id> exs = insertFun.colAll(models).exs();
        if(exs != null){
            return exs.stream().map(Id::toLong).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public int remove(T model) {
        DeleteWhereFun<T, GetterFun<T, Object>, Object> where = deleteFun.where();
        AtomicBoolean status = parseWhere(model, where);
        if(status.get()){
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
        Set<Serializable> idSet = Arrays.stream(ids).collect(Collectors.toSet());
        return removeByIds(idSet);
    }

    @Override
    public int removeByIds(Collection<Serializable> ids) {
        TableInfo tableInfo = getTableInfo(mClass);
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        DeleteWhereFun<T, GetterFun<T, Object>, Object> where = deleteFun.where();
        AtomicBoolean status = new AtomicBoolean(false);
        primaryColNames.forEach((colName, colInfo)->{
            if(!status.get()){
                status.set(true);
                Map<String, Object> param = new HashMap<>();
                String newColNameTag = getNewColNameTag();
                param.putIfAbsent(newColNameTag, ids);
                where.condSQL(colName + " in ( #{"+newColNameTag+"} ) ", param);
            }
        });
        if(status.get()){
            return where.ex();
        }
        return 0;
    }

    @Override
    public int modifyById(T model) {
        if(model != null){
            LinkedList<T> models = new LinkedList<>();
            models.add(model);
            return modifyBatchById(models);
        }
        return 0;
    }

    @Override
    public int modifyBatchById(Collection<T> models) {
        if(models == null || models.size() == 0){
            return 0;
        }
        models = models.stream().filter(Objects::nonNull).collect(Collectors.toList());
        WhereModifyFun<T, V> where = null;
        AtomicBoolean status = new AtomicBoolean(false);
        SmartUpdateFun<T, C, V> npdateNull = this.updateFun.npdateNull();
        int i = 0;
        for( T model : models){
            if(i==0){
                where= npdateNull.colAll(model).where();
            }else{
                where.addBatch().colAll(model).where();
            }
            i++;
            AtomicBoolean status_ = new AtomicBoolean(false);
            parseWhereById(where, status_, model);
            if(!status_.get()){
                //This statement should be ignored and should not execute successfully
                where.condSQL(" 1 = 2 ");
            }else{
                status.set(true);
            }
        }
        if(status.get()){
            return where.ex().intValue();
        }
        return 0;
    }


    @Override
    public List<T> query(T model) {
        WhereSelectFun<T, Object> where = parseQueryWhere(model);
        return where.exs();
    }

    @Override
    public List<T> query(T model, int pageNum, int pageSize) {
        WhereSelectFun<T, Object> where = parseQueryWhere(model);
        where.limitPage(pageNum,pageSize);
        return where.exs();
    }

    @Override
    public List<T> query(int pageNum, int pageSize) {
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize <= 0){
            pageSize = 10;
        }
        List<T> exs = this.selectFun.colAll().where().limitPage(pageNum, pageSize).exs();
        if(exs == null){
            exs = Collections.EMPTY_LIST;
        }
        return exs;
    }

    @Override
    public T queryById(Serializable id) {
        List<T> ts = queryByIds(id);
        if(ts == null || ts.size() == 0){
            return null;
        }
        return ts.get(0);
    }

    @Override
    public List<T> queryByIds(Serializable... ids) {
        Set<Serializable> idSet = Arrays.stream(ids).collect(Collectors.toSet());
        return queryByIds(idSet);
    }

    @Override
    public List<T> queryByIds(Collection<Serializable> ids) {
        WhereSelectFun<T, Object> where = this.selectFun.colAll().where();
        Map<String, ColumnInfo> primaryColNames = TableHelper.getTableInfo(mClass).getPrimaryColNames();
        AtomicBoolean status = new AtomicBoolean(false);
        primaryColNames.forEach((colName, colInfo)->{
            if(!status.get()){
                HashMap<String, Object> param = new HashMap<>();
                String newColNameTag = getNewColNameTag();
                param.put(newColNameTag,ids);
                status.set(true);
                where.condSQL(colName+" in ( #{"+newColNameTag+"} ) ",param);
            }
        });
        if(status.get()){
            List<T> exs = where.exs();
            if(exs!=null && exs.size()>0){
                return exs;
            }
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<T> queryByParam(Map<String, Object> param) {
        return queryByParam(param,-1,-1,false);
    }

    @Override
    public List<T> queryByParam(Map<String, Object> param, int pageNum, int pageSize) {
        return queryByParam(param,pageNum,pageSize,true);
    }

    @Override
    public long count() {
        TableInfo tableInfo = TableHelper.getTableInfo(mClass);
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        Map<String, List<Field>> originalColNameOfModelField = tableInfo.getOriginalColNameOfModelField();
        String primaryColName = primaryColNames.keySet().iterator().next();
        Field field = originalColNameOfModelField.get(primaryColName).get(0);
        String aliasName = field.getName();
        T ex = this.selectFun.col("count("+primaryColName+") as " + aliasName).where().ex();
        return getNumber(field, ex);
    }

    @Override
    public long count(C c) {
        Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(c);
        String aliasName = colNameAndAliasName.getRight();
        Field field = TableHelper.getTableInfo(mClass).getFieldNameAndField().get(aliasName);
        T ex = this.selectFun.col(AggTag.COUNT, c).where().ex();
        return  getNumber(field,ex);
    }

    private WhereSelectFun<T, Object> parseQueryWhere(T model) {
        WhereSelectFun<T, Object> where = this.selectFun.colAll().where();
        parseWhere(model, where);
        return where;
    }

    private List<T> queryByParam(Map<String, Object> param, int pageNum, int pageSize, boolean isPage) {
        if(param != null && param.size() > 0){
            WhereSelectFun<T, Object> where = selectFun.colAll().where();
            AtomicBoolean status = new AtomicBoolean(false);
            param.forEach((colName, value)->{
                Map<String, Object> param_ = new HashMap<>();
                String newColNameTag = getNewColNameTag();
                param_.put(newColNameTag, value);
                where.condSQL(colName + " in ( #{"+newColNameTag+"} ) ",param_);
                status.set(true);
            });
            if(status.get()){
                if(isPage){
                    where.limitPage(pageNum,pageSize);
                }
                List<T> exs = where.exs();
                if(exs != null){
                    return exs;
                }
            }
        }
        return Collections.EMPTY_LIST;
    }

    private String getNewColNameTag() {
        return ato.getAndIncrement()+"";
    }

    private TableInfo getTableInfo(Class modelClass) {
        TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
        Assert.isTrue(tableInfo != null,"The table information corresponding to this class cannot be queried");
        return tableInfo;
    }

    private AtomicBoolean parseWhere(T model, WhereFun where) {
        TableInfo tableInfo = getTableInfo(model.getClass());
        Map<String, ColumnInfo> originalColNames = tableInfo.getColNames();
        Map<String, List<Field>> colNameOfModelField = tableInfo.getColNameAndFieldOfModel();
        AtomicBoolean status = new AtomicBoolean(false);

        if(colNameOfModelField != null && colNameOfModelField.size() > 0){
            colNameOfModelField.forEach((colName, fields)->{
                Object o = null;
                try {
                    for(int i=0; fields != null && i<fields.size();i++){
                        Field field = fields.get(i);
                        o = field.get(model);
                        if(o != null){
                            if(originalColNames.get(colName) !=null
                                    && originalColNames.get(colName).isAutoincrement()
                                    && o instanceof Number
                                    && ((Number) o).longValue() == 0L){
                                continue;
                            }
                            break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(o != null){
                    HashMap<String, Object> param = new HashMap<>();
                    String newColNameTag = getNewColNameTag();
                    param.putIfAbsent(newColNameTag,o);
                    where.condSQL(colName+" in ( #{"+newColNameTag+"} ) ", param);
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
        primaryColNames.forEach((colName, colInfo)->{
            List<Field> fields = colNameOfModelField.get(colName);
            Object o = null;
            for (Field field : fields){
                try {
                    o = field.get(model);
                    if(o!=null){
                        if(o instanceof Number && colInfo.isAutoincrement() && ((Number) o).longValue() == 0L){
                            continue;
                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new GetColValueException(e.getMessage());
                }
            }
            if(o != null){
                Map<String, Object> param = new HashMap<>();
                String newColNameTag = getNewColNameTag();
                param.put(newColNameTag, o);
                finalWhere.condSQL(colName + " in ( #{"+newColNameTag+"} ) ",param);
                status.set(true);
            }
        });
    }

    private long getNumber(Field field, T ex) {
        if(ex == null){
            return 0;
        }
        Object o = null;
        try {
            o = field.get(ex);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
        if(o instanceof Number){
            return ((Number) o).longValue();
        }else{
            return Long.parseLong(o.toString());
        }
    }
}
