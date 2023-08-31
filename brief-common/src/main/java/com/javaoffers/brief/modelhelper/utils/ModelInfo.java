package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.anno.fun.params.math.Mod;
import com.javaoffers.brief.modelhelper.exception.ParseModelException;
import com.javaoffers.brief.modelhelper.exception.ParseResultSetException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * for mapping ResultSet 2 Model
 *
 * @author mingJie
 */
public class ModelInfo<T> {
    Class<T> modelClass;
    Newc<T> constructor;
    Map<String, ModelFieldInfo> onesColNameMap = new HashMap<>();
    List<ModelFieldInfo> onesModels= new ArrayList<ModelFieldInfo>();
    Map<String, ModelFieldInfo> uniqueColNameMap = new HashMap<>();
    List<ModelFieldInfo> uniqueModels = new ArrayList<ModelFieldInfo>();
    List<ModelFieldInfo> arraysModels = new ArrayList<ModelFieldInfo>();
    List<ModelFieldInfo> listModels = new ArrayList<ModelFieldInfo>();
    List<ModelFieldInfo> setModels = new ArrayList<ModelFieldInfo>();
    List<ModelFieldInfo> gkeyUniqueModels = new ArrayList<>();

    public ModelInfo(Class<T> modelClass) {
        this.modelClass = modelClass;

        ArrayList<ModelFieldInfo> ones = new ArrayList<ModelFieldInfo>();//非数组或集合model,一对一 (onesColName + onesModel)
        ArrayList<ModelFieldInfo> arrays = new ArrayList<ModelFieldInfo>();//存放数组model
        ArrayList<ModelFieldInfo> list = new ArrayList<ModelFieldInfo>();//存放list集合model
        ArrayList<ModelFieldInfo> set = new ArrayList<ModelFieldInfo>();//存放set集合model

        Field[] colFs = Utils.getFields(modelClass).toArray(new Field[]{});
        for (Field fd : colFs) {
            if (fd.getType().isArray()) {
                arrays.add(new ModelFieldInfo(fd, this.modelClass));
            } else if (List.class.isAssignableFrom(fd.getType())) {
                list.add(new ModelFieldInfo(fd, this.modelClass));
            } else if (Set.class.isAssignableFrom(fd.getType())) {
                set.add(new ModelFieldInfo(fd, this.modelClass));
            } else {
                ones.add(new ModelFieldInfo(fd, this.modelClass));
            }
        }
        try {
            for (ModelFieldInfo modelFieldInfo : ones) {
                ModelFieldInfo fieldNameAsAliasNameFieldInfo = modelFieldInfo.cloneFieldNameAsAliasName();
                if (modelFieldInfo.isModelClass()) {
                    addModel(onesModels, modelFieldInfo);
                    if (modelFieldInfo.isUniqueField()) {
                        addModel(uniqueModels, modelFieldInfo);
                    }
                } else {
                    put(onesColNameMap, modelFieldInfo, fieldNameAsAliasNameFieldInfo);
                    if (modelFieldInfo.isUniqueField()) {
                        put(uniqueColNameMap, modelFieldInfo, fieldNameAsAliasNameFieldInfo);
                    }
                    //添加自动生成key策略
                    if(modelFieldInfo.isAtoGkey()){
                        gkeyUniqueModels.add(modelFieldInfo);
                    }
                }
            }
            addModel(arraysModels, arrays);
            addModel(listModels, list);
            addModel(setModels, set);
            this.constructor = LambdaCreateUtils.createConstructor(modelClass);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ParseModelException(e.getMessage());
        }

    }

    public void put(Map<String, ModelFieldInfo> map, ModelFieldInfo... modelFieldInfos) {
        for (ModelFieldInfo modelFieldInfo : modelFieldInfos) {
            map.put(modelFieldInfo.getAliasName(), modelFieldInfo);
        }
    }

    public void addModel(List<ModelFieldInfo> modes, ArrayList<ModelFieldInfo> listTmp) {
        for (ModelFieldInfo modelFieldInfo : listTmp) {
            if (modelFieldInfo.isModelClass()) {
                modes.add(modelFieldInfo);
            }
        }
    }

    public void addModel(List<ModelFieldInfo> modes, ModelFieldInfo... modelFieldInfos) {
        for (ModelFieldInfo modelFieldInfo : modelFieldInfos) {
            if (modelFieldInfo.isModelClass()) {
                modes.add(modelFieldInfo);
            }
        }
    }

    /**
     * new instance
     *
     * @return
     */
    public T newC() {
        return constructor.newc();
    }

    public Class<T> getModelClass() {
        return modelClass;
    }

    public Newc<T> getConstructor() {
        return constructor;
    }

    //获取一对一model和当前表的字段colName
    public List<ModelFieldInfoPosition> getOnesColWithOneModel(List<String> colNames) {
        List<ModelFieldInfoPosition> ones = new ArrayList<>();
        for (int i = 0; i < colNames.size(); ) {
            String colName = colNames.get(i);
            ModelFieldInfo modelFieldInfo = onesColNameMap.get(colName);
            ++i;
            if (modelFieldInfo != null) {
                ones.add(new ModelFieldInfoPosition(i, modelFieldInfo));
            }

        }
        List<ModelFieldInfoPosition> oneModelTmp = onesModels.stream().filter(
                oneFieldModel->{
                    ModelInfo modelInfo = TableHelper.getModelInfo(oneFieldModel.getModelClassOfField());
                    return modelInfo.getOnesColWithOneModel(colNames).size()>0 ; }
        ).map(modelFieldInfo -> {
            return new ModelFieldInfoPosition(-1, modelFieldInfo);
        }).collect(Collectors.toList());

        ones.addAll(oneModelTmp);
        return ones;
    }
    //当前表的字段colName
    public List<ModelFieldInfoPosition> getOnesCol(List<String> colNames) {
        List<ModelFieldInfoPosition> ones = new ArrayList<>();
        for (int i = 0; i < colNames.size(); ) {
            String colName = colNames.get(i);
            ModelFieldInfo modelFieldInfo = onesColNameMap.get(colName);
            ++i;
            if (modelFieldInfo != null && !modelFieldInfo.isModelClass()) {
                ones.add(new ModelFieldInfoPosition(i, modelFieldInfo));
            }
        }
        return ones;
    }

    public List<ModelFieldInfo> getArrays(List<String> colNames) {
        return arraysModels.stream().filter(
                filter(colNames)
        ).collect(Collectors.toList());
    }

    public List<ModelFieldInfo> getList(List<String> colNames) {
        return this.listModels.stream().filter(
                filter(colNames)
        ).collect(Collectors.toList());
    }

    public List<ModelFieldInfo> getSet(List<String> colNames) {
        return setModels.stream().filter(
                filter(colNames)
        ).collect(Collectors.toList());
    }

    public List<ModelFieldInfoPosition> getUniqueCol(List<String> colNames) {
        List<ModelFieldInfoPosition> unique = new ArrayList<>();
        for (int i = 0; i < colNames.size(); ) {
            String colName = colNames.get(i);
            ModelFieldInfo modelFieldInfo = uniqueColNameMap.get(colName);
            ++i;
            if (modelFieldInfo != null) {
                unique.add(new ModelFieldInfoPosition(i, modelFieldInfo));
            }
        }
        return unique;
    }

    public List<ModelFieldInfo> getGkeyUniqueModels() {
        return gkeyUniqueModels;
    }

    private Predicate<ModelFieldInfo> filter(List<String> colNames) {
        return modelFieldInfo -> {
            ModelInfo modelInfo = TableHelper.getModelInfo(modelFieldInfo.getModelClassOfField());
            return modelInfo.getOnesCol(colNames).size()>0 ;
        };
    }
}
