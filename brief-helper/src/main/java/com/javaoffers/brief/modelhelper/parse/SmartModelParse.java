package com.javaoffers.brief.modelhelper.parse;

import com.javaoffers.brief.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.brief.modelhelper.exception.BaseException;
import com.javaoffers.brief.modelhelper.util.HelperUtils;
import com.javaoffers.brief.modelhelper.util.Model;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description: model解析器
 * @Auther: create by cmj on 2021/12/7 19:59
 */
public class SmartModelParse implements ModelParse {

    private final static ConvertRegisterSelectorDelegate convert = ConvertRegisterSelectorDelegate.convert;

    private final static ThreadLocal<Map<String,String>> tl = new ThreadLocal<Map<String,String>>();

    /**
     * 关于list需要优化
     * @param clazz
     * @param listMap
     * @param <E>
     * @return
     */
    @Override
    public <E> List<E> converterMap2Model(Class<E> clazz, List<Map<String, Object>> listMap) {
        if (listMap == null || listMap.size() == 0) {
            return new ArrayList<E>();
        }
        if (HelperUtils.isBaseModel(clazz)) {// model
            try {
                return buildModel(clazz, listMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Collections.EMPTY_LIST;
    }

    /**用于原始数据集转换Model数据集
     * @param clazz  Model的Class对象
     * @param list_map      封装后的Model数据集
     * @throws Exception
     * @throws SecurityException
     */
    private static <E> List<E> buildModel(Class<E> clazz, List<Map<String, Object>> list_map) throws Exception {
        LinkedList<E> linkedList = new LinkedList<E>();
        if(clazz==null||list_map==null||list_map.size()==0) {
            return linkedList;
        }
        Map<String, List<Map<String, Object>>> map_ = new LinkedHashMap<>();//存放切分后的数据（根据唯一字段的值分组）
        Set<Field> fields = HelperUtils.getFields(clazz);
        //checkIsExistsSameSuperModel(clazz);
        ArrayList<Field> ones = new ArrayList<Field>();//非数组或集合model,一对一
        ArrayList<Field> arrays = new ArrayList<Field>();//存放数组model
        ArrayList<Field> list_ = new ArrayList<Field>();//存放list集合model
        ArrayList<Field> set_ = new ArrayList<Field>();//存放set集合model
        for(Field fd : fields) {
            if(fd.getType().isArray()) {
                arrays.add(fd);
            }else if(List.class.isAssignableFrom(fd.getType())) {
                list_.add(fd);
            }else if(Set.class.isAssignableFrom(fd.getType())) {
                set_.add(fd);
            }else {
                ones.add(fd);
            }
        }
        String tableName = TableHelper.getTableName(clazz);
        ArrayList<String> uniqueFieldNameList = new ArrayList<String>();//Stores fields that can determine a piece of main table data (main model)
        HelperUtils.getUniqueFieldNames(clazz, tableName, ones, uniqueFieldNameList,list_map.get(0));//Get the fields that can determine a piece of main table data (main model)
        if(uniqueFieldNameList==null || uniqueFieldNameList.size()==0){
            return linkedList;
        }
        HelperUtils.inciseData(list_map, map_, uniqueFieldNameList);//Cutting data
        List<E> list = buildData(tableName, clazz,map_,ones,arrays,list_,set_);
        linkedList.addAll(list);
        return linkedList;
    }



    @SuppressWarnings("unchecked")
    private static <E> List<E> buildData(
            String tableName,
            Class<E> clazz,Map<String,
            List<Map<String, Object>>> map_,
            ArrayList<Field> ones,
            ArrayList<Field> arrays,
            ArrayList<Field> list_,
            ArrayList<Field> set_) throws Exception {
        LinkedList<E> linkedList = new LinkedList<E>();

        try {
            Set<Map.Entry<String,List<Map<String,Object>>>> entrySet = map_.entrySet();
            for(Map.Entry<String,List<Map<String,Object>>> entry : entrySet) {

                List<Map<String,Object>> list = entry.getValue();//Get a cut data
                if(list==null||list.size()==0) {
                    continue;
                }
                Map<String, Object> mp = list.get(0);//Get the unique cutting data, just take 0, which refers to the main model main data
                Model<E> model = Model.getModel(clazz.newInstance());

                for(Field fd: ones) {
                    if(HelperUtils.isBaseModel(fd)) {
                        List ls = buildModel(HelperUtils.getModelClass(fd), list);
                        if(ls!=null&&ls.size()==1) {
                            Object object = ls.get(0);
                            fd.set(model.getModelAndSetStatusIsTrue(), object);
                        }
                        continue;
                    }
                    String name = HelperUtils.getSpecialColName(tableName,fd.getName());
                    Object o = null;
                    if((o = mp.get(name)) !=null || (o = mp.get(fd.getName())) !=  null
                            || (o = mp.get(HelperUtils.getSpecialColName(clazz,fd))) != null) {
                        Class<?> type = fd.getType();
                        Object object2 = convert.converterObject(type,o ,fd);
                        fd.set(model.getModelAndSetStatusIsTrue(), object2);
                    }

                    continue;
                }
                for(Field fd: arrays) {
                    if(HelperUtils.isBaseModel(fd)) {
                        List<E> ls = buildModel(HelperUtils.getModelClass(fd), list);
                        if(ls!=null&&!ls.isEmpty()&&ls.size()>0) {
                            Class<? extends Object> class1 = ls.get(0).getClass();
                            Object object = Array.newInstance(class1, ls.size());
                            for(int i=0;i<ls.size();i++) {
                                Array.set(object, i, ls.get(i));
                            }
                            if(object!=null) {
                                fd.set(model.getModelAndSetStatusIsTrue(),object );
                            }
                        }
                    }else {
                        String name = HelperUtils.getSpecialColName(tableName,fd.getName());
                        LinkedList<Object> arrayData = new LinkedList<Object>();
                        for(Map<String, Object> map : list) {
                            Object object = map.get(name);
                            if(object == null){
                                object = map.get(fd.getName());
                            }
                            if(object == null){
                                object = map.get(HelperUtils.getSpecialColName(clazz,fd));
                            }
                            if(object!=null) {
                                arrayData.add(object);
                            }
                        }
                        String typeName = fd.getGenericType().getTypeName();
                        Class<?> originClass = Class.forName(typeName.substring(0, typeName.length()-2));
                        Object object = Array.newInstance(originClass, arrayData.size());
                        if(arrayData!=null&&arrayData.size()>0) {
                            for(int i=0;i<arrayData.size();i++) {
                                Object object2 = convert.converterObject(originClass.getClass(), arrayData.get(i),fd);
                                Array.set(object, i, object2);
                            }
                            fd.set(model.getModelAndSetStatusIsTrue(),object );
                        }
                    }
                }
                for(Field fd: list_) {
                    Class<?> type = fd.getType();
                    List ls = Collections.EMPTY_LIST;
                    if(type.isInterface()) {
                        if(HelperUtils.isBaseModel(fd)){
                            ls = buildModel(HelperUtils.getModelClass(fd), list);
                        }else{
                            ls = getList(clazz, tableName,list, fd);
                        }
                    }else {
                        if(HelperUtils.isBaseModel(fd)){
                            ls = buildModel(HelperUtils.getModelClass(fd), list);
                        }else{
                            ls = getList(clazz, tableName, list, fd);
                        }
                        List newInstance = (List)fd.get(model.getModelAndSetStatusIsTrue());
                        if(newInstance==null){
                            newInstance = (List)type.newInstance();
                        }
                        newInstance.addAll(ls);
                        ls = newInstance;
                    }

                    if(ls!=null&&ls.size()>0) {
                       fd.set(model.getModelAndSetStatusIsTrue(), ls);
                    }

                }
                for(Field fd: set_) {
                    Class<?> type = fd.getType();
                    List ls = Collections.EMPTY_LIST;
                    Set newInstance = Collections.EMPTY_SET;
                    if(type.isInterface()) {

                        if(HelperUtils.isBaseModel(fd)){
                            ls = buildModel(HelperUtils.getModelClass(fd), list);
                        }else {
                            ls = getList(clazz, tableName, list, fd);
                        }
                        newInstance = new HashSet();
                        newInstance.addAll(ls);
                    }else {
                        if(HelperUtils.isBaseModel(fd)){
                            ls = buildModel(HelperUtils.getModelClass(fd), list);
                        }else {
                            ls = getList(clazz, tableName, list, fd);
                        }
                        newInstance = (Set)fd.get(model.getModelAndSetStatusIsTrue());
                        if(newInstance == null){
                            newInstance = (Set)type.newInstance();
                        }
                        newInstance.addAll(ls);
                    }
                    if(newInstance != null && newInstance.size() > 0) {
                        fd.set(model.getModelAndSetStatusIsTrue(), newInstance);
                    }
                }
                if(model.isStatus()) {
                    linkedList.add(model.getModel());
                }

            }

        }catch(Exception ex) {
            throw ex;
        }
        return linkedList;
    }

    private static List getList(Class mClass, String tableName , List<Map<String, Object>> list, Field fd) throws ClassNotFoundException {
        List ls;
        Class gClass = HelperUtils.getGenericityClassOfCollect(fd);//泛型类型
        ls = new LinkedList();
        List finalLs = ls;
        list.forEach(map->{
            Object o = map.get(HelperUtils.getSpecialColName(tableName, fd.getName()));
            if(o == null){
                o = map.get(fd.getName());
            }
            if(o == null){
                o = map.get(HelperUtils.getSpecialColName(mClass, fd));
            }
            finalLs.add(convert.converterObject(gClass,o,fd));
       });
        return ls;
    }

    /**
     *  string： mianmodel class name
     *  String ： Save the name of the parent class of the current Model. If the same exists, it will prompt that the Model has the same parent class.
     */
    @Deprecated
    private static <E> void checkIsExistsSameSuperModel(Class<E> clazz) {
        Map<String, String> map = tl.get();
        final String name = clazz.getSuperclass().getName();

        if(map == null){
            map = new HashMap<>();
        }
        if("java.lang.Object".equals(name)){
            return ;
        }else{
            final String superName = map.get(name);
            if(StringUtils.isNotBlank(superName)){
                throw new BaseException("Model models cannot have the same parent class to prevent field duplication! !"+clazz.getName());
            }else{
                map.put(name,name);
            }
        }

    }




}
