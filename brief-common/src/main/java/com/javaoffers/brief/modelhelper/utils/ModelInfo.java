package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.exception.ParseModelException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * for mapping ResultSet 2 Model
 * @author mingJie
 */
public class ModelInfo<T> {
    Class<T> modelClass;
    Newc<T> constructor;
    ArrayList<ModelFieldInfo> ones = new ArrayList<ModelFieldInfo>();//非数组或集合model,一对一
    ArrayList<ModelFieldInfo> arrays = new ArrayList<ModelFieldInfo>();//存放数组model
    ArrayList<ModelFieldInfo> list = new ArrayList<ModelFieldInfo>();//存放list集合model
    ArrayList<ModelFieldInfo> set = new ArrayList<ModelFieldInfo>();//存放set集合model
    ArrayList<ModelFieldInfo> unique = new ArrayList<>();//unique field

    public ModelInfo(Class<T> modelClass) {
        this.modelClass = modelClass;
        ArrayList<ModelFieldInfo> ones = new ArrayList<ModelFieldInfo>();//非数组或集合model,一对一
        ArrayList<ModelFieldInfo> arrays = new ArrayList<ModelFieldInfo>();//存放数组model
        ArrayList<ModelFieldInfo> list = new ArrayList<ModelFieldInfo>();//存放list集合model
        ArrayList<ModelFieldInfo> set = new ArrayList<ModelFieldInfo>();//存放set集合model
        Field[] colFs = Utils.getFields(modelClass).toArray(new Field[]{});
        for(Field fd : colFs) {
            if(fd.getType().isArray()) {
                arrays.add(new ModelFieldInfo(fd, this.modelClass));
            }else if(List.class.isAssignableFrom(fd.getType())) {
                list.add(new ModelFieldInfo(fd,this.modelClass));
            }else if(Set.class.isAssignableFrom(fd.getType())) {
                set.add(new ModelFieldInfo(fd,this.modelClass));
            }else {
                ones.add(new ModelFieldInfo(fd,this.modelClass));
            }
        }

        if(ones != null){
            this.ones = ones;
        }
        if(arrays != null){
            this.arrays = arrays;
        }
        if(list != null){
            this.list = list;
        }
        if(set != null){
            this.set = set;
        }
        try {
            for(ModelFieldInfo modelFieldInfo : ones){
                if(modelFieldInfo.isUniqueField()){
                    unique.add(modelFieldInfo);
                }
            }
            this.constructor = LambdaCreateUtils.createConstructor(modelClass);
        }catch (Throwable e){
            e.printStackTrace();
            throw new ParseModelException(e.getMessage());
        }
    }

    /**
     * new instance
     * @return
     */
    public T newC(){
        return constructor.newc();
    }

    public Class<T> getModelClass() {
        return modelClass;
    }

    public Newc<T> getConstructor() {
        return constructor;
    }

    public ArrayList<ModelFieldInfo> getOnes() {
        return ones;
    }

    public ArrayList<ModelFieldInfo> getArrays() {
        return arrays;
    }

    public ArrayList<ModelFieldInfo> getList() {
        return list;
    }

    public ArrayList<ModelFieldInfo> getSet() {
        return set;
    }

    public ArrayList<ModelFieldInfo> getUnique() {
        return unique;
    }

    public List<ModelFieldInfo> getOnes(List<String> colNames) {
        return ones.stream().filter(
                filter(colNames)
        ).collect(Collectors.toList());
    }

    public List<ModelFieldInfo> getArrays(List<String> colNames) {
        return arrays;
    }

    public List<ModelFieldInfo> getList(List<String> colNames) {
        return list.stream().filter(
                filter(colNames)
        ).collect(Collectors.toList());
    }

    public List<ModelFieldInfo> getSet(List<String> colNames) {
        return set.stream().filter(
                filter(colNames)
        ).collect(Collectors.toList());
    }

    public List<ModelFieldInfo> getUnique(List<String> colNames) {
        List<ModelFieldInfo>  uniqueList = unique.stream().filter(
                filter(colNames)
        ).collect(Collectors.toList());
        if(uniqueList.size() != unique.size()){
            return getOnes(colNames);
        }
        return uniqueList;
    }

    private Predicate<ModelFieldInfo> filter(List<String> colNames) {
        return modelFieldInfo -> {
            boolean a = colNames.contains(modelFieldInfo.getAliasName())
                    || colNames.contains(modelFieldInfo.getFieldName());
            if(a){
                return a;
            }
            if(modelFieldInfo.isModelClass()){
                ModelInfo modelInfo = TableHelper.getModelInfo(modelFieldInfo.getModelClassOfField());
                return modelInfo.getOnes(colNames).size()>0 ;
            }
            return false;
        };
    }

}
