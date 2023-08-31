package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import com.javaoffers.brief.modelhelper.core.ConvertProxy;
import com.javaoffers.brief.modelhelper.core.KeyGenerate;
import com.javaoffers.brief.modelhelper.core.UniqueKeyGenerate;
import com.javaoffers.brief.modelhelper.core.gkey.VoidKey;
import com.javaoffers.brief.modelhelper.exception.ParseModelException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 避免反射.
 * @author mingJie
 */
public class ModelFieldInfo {

    private boolean isModelClass;

    /**
     * 声明的model类型，该field属于此model中的一个field字段
     */
    private Class modelClass;

    /**
     * field真实类型. 如果是数组或者集合，则是真实的类型为元素类型。
     */
    private Class fieldGenericClass;

    /**
     * field的类型,可能会是集合
     */
    private Class fieldType;

    /**
     * tableInfo
     */
    private TableInfo tableInfo;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 反射field字段
     */
    private Field field;

    /**
     * setter方法
     */
    private Setter setter;

    /**
     * getter方法
     */
    private Getter getter;

    /**
     * field名称
     */
    private String fieldName;

    /**
     * colName as  aliasName
     */
    private String aliasName;

    /**
     * @BaseUnique
     */
    private boolean isUniqueField;

    /**
     * 是否是抽象类型，通常用于Set,List
     */
    private boolean fieldTypeIsAbstract;

    /**
     * list / set 构造方法. 当fieldType为List/Set时才有效
     */
    private Newc newc;

    /**
     * 后置设置.当数据在第一次渲染的时候会被设置.
     */
    private ConvertProxy convertProxy;

    /**
     * 生成唯一key策略
     */
    private boolean isAtoGkey;

    /**
     * key生成器
     */
    private UniqueKeyGenerate uniqueKeyGenerate;

    private ModelFieldInfo() {
    }

    public ModelFieldInfo(Field field, Class modelClass) {
        this.field = field;
        this.modelClass = modelClass;
        init();
    }

    private void init(){
        this.field.setAccessible(true);
        try {
            this.isModelClass = Utils.isBaseModel(this.field);
            this.setter = LambdaCreateUtils.createSetter(field);
            this.getter = LambdaCreateUtils.createGetter(field);
            this.fieldName = this.field.getName();
            this.tableInfo = TableHelper.getTableInfo(this.modelClass);
            this.tableName = this.tableInfo.getTableName();
            this.aliasName = Utils.getSpecialColName(this.tableName, tableInfo.getDbType(), this.fieldName);
            BaseUnique baseUnique = this.field.getDeclaredAnnotation(BaseUnique.class);
            this.isUniqueField =  baseUnique != null;
            this.fieldGenericClass = Utils.getGenericityClass(field);
            this.fieldType = this.field.getType();
            this.fieldTypeIsAbstract = this.fieldType.isInterface();
            if(this.fieldTypeIsAbstract){
                if(List.class.isAssignableFrom(this.fieldType)){
                    newc = ArrayList::new;
                }else if(Set.class.isAssignableFrom(this.fieldType)){
                    newc = HashSet::new;
                } else if(Collection.class.isAssignableFrom(this.fieldType)){
                    newc = ArrayList::new;
                }
            }else if(Collection.class.isAssignableFrom(this.fieldType)){
                newc = LambdaCreateUtils.createConstructor(this.fieldType);
            }

            //处理自动生成主键
            if(this.isUniqueField){
                Class<? extends UniqueKeyGenerate> keyGenerateClass = baseUnique.keyGenerateClass();
                KeyGenerate keyGenerate = baseUnique.keyGenerate();
                if(keyGenerateClass != VoidKey.class){
                    uniqueKeyGenerate = keyGenerateClass.newInstance();
                }else if(keyGenerate != keyGenerate.VOID_KEY){
                    Class<UniqueKeyGenerate> gkeyClass = keyGenerate.getGkeyClass();
                    uniqueKeyGenerate = gkeyClass.newInstance();
                }

                this.isAtoGkey = this.uniqueKeyGenerate != null;
            }

        }catch (Throwable e){
            e.printStackTrace();
            throw new ParseModelException(e.getMessage());
        }
    }

    public ModelFieldInfo cloneFieldNameAsAliasName(){
        ModelFieldInfo modelFieldInfo = new ModelFieldInfo();
        modelFieldInfo.isModelClass = this.isModelClass;
        modelFieldInfo.modelClass = this.modelClass;
        modelFieldInfo.fieldGenericClass = this.fieldGenericClass;
        modelFieldInfo.fieldType = this.fieldType;
        modelFieldInfo.tableInfo = this.tableInfo;
        modelFieldInfo.tableName = this.tableName;
        modelFieldInfo.field = this.field;
        modelFieldInfo.setter = this.setter;
        modelFieldInfo.getter = this.getter;
        modelFieldInfo.fieldName = this.fieldName;
        modelFieldInfo.aliasName = this.fieldName;//fieldName as aliasName
        modelFieldInfo.isUniqueField = this.isUniqueField;
        modelFieldInfo.fieldTypeIsAbstract = this.fieldTypeIsAbstract;
        modelFieldInfo.newc = this.newc;
        return modelFieldInfo;

    }

    public Class getModelClassOfField() {
        if(!this.isModelClass){
            return null;
        }
        return this.fieldGenericClass;
    }

    public boolean isModelClass(){
        return this.isModelClass;
    }

    public Field getField() {
        return field;
    }

    public Setter getSetter() {
        return setter;
    }

    public Class getModelClass() {
        return modelClass;
    }

    public String getTableName() {
        return tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public boolean isUniqueField() {
        return isUniqueField;
    }

    public Class getFieldGenericClass() {
        return fieldGenericClass;
    }

    public Getter getGetter() {
        return getter;
    }

    public boolean isAbstractFieldType() {
        return fieldTypeIsAbstract;
    }

    /**
     * 该对象为List/Set/Collection
     * @return
     */
    public Object getNewc(){
        return newc.newc();
    }

    public ConvertProxy getConvertProxy() {
        return convertProxy;
    }

    public void setConvertProxy(ConvertProxy convertProxy) {
        if(convertProxy != null){
            this.convertProxy = convertProxy;
        }
    }

    public boolean isAtoGkey() {
        return isAtoGkey;
    }

    public UniqueKeyGenerate getUniqueKeyGenerate() {
        return uniqueKeyGenerate;
    }
}
