package com.javaoffers.batis.modelhelper.parse;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import com.javaoffers.batis.modelhelper.exception.BaseException;
import com.javaoffers.batis.modelhelper.util.Utils;
import com.javaoffers.batis.modelhelper.util.Model;
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
    public <E> ArrayList<E> converterMap2Model(Class<E> clazz, List<Map<String, Object>> listMap) {
        if (listMap == null || listMap.size() == 0) {
            return new ArrayList<E>();
        }
        ArrayList<E> list = new ArrayList<E>();
        if (clazz.getDeclaredAnnotation(BaseModel.class) != null) {// model
            try {
                list.addAll(buildModel(clazz, Utils.initData(listMap)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
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
        Set<Field> fields = Utils.getFields(clazz);
        checkIsExistsSameSuperModel(clazz);
        ArrayList<Field> ones = new ArrayList<Field>();//非数组或集合model,一对一
        ArrayList<Field> arrays = new ArrayList<Field>();//存放数组model
        ArrayList<Field> list_ = new ArrayList<Field>();//存放list集合model
        ArrayList<Field> set_ = new ArrayList<Field>();//存放set集合model
        for(Field fd : fields) {
            fd.setAccessible(true);
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
        ArrayList<String> uniqueFieldNameList = new ArrayList<String>();//存放能确定一条主表数据（主model）的字段
        Utils.getUniqueFieldNames(ones, uniqueFieldNameList,list_map.get(0));//获取能确定一条主表数据（主model）的字段
        Utils.inciseData(list_map, map_, uniqueFieldNameList);//切割数据
        List<E> list = buildData(clazz,map_,ones,arrays,list_,set_);
        linkedList.addAll(list);
        return linkedList;
    }

    @SuppressWarnings("unchecked")
    private static <E> List<E> buildData(
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

                List<Map<String,Object>> list = entry.getValue();//获取一切割数据
                if(list==null||list.size()==0) {
                    continue;
                }
                Map<String, Object> mp = list.get(0);//获取唯一切割数据，只取0即可，指主model主数据
                Model<E> model = Model.getModel(clazz.newInstance());

                for(Field fd: ones) {
                    if(Utils.isBaseModel(fd)) {
                        List ls = buildModel(Utils.getModelClass(fd), list);
                        if(ls!=null&&ls.size()==1) {
                            Object object = ls.get(0);
                            fd.set(model.getModelAndSetStatusIsTrue(), object);
                        }
                        continue;
                    }
                    String name = fd.getName();
                    if(mp.get(name)!=null) {
                        Class<?> type = fd.getType();
                        Object object2 = convert.converterObject(type, mp.get(name),fd);
                        fd.set(model.getModelAndSetStatusIsTrue(), object2);
                    }

                    continue;
                }
                for(Field fd: arrays) {
                    if(Utils.isBaseModel(fd)) {
                        List<E> ls = buildModel(Utils.getModelClass(fd), list);
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
                        String name = fd.getName();
                        LinkedList<Object> arrayData = new LinkedList<Object>();
                        for(Map<String, Object> map : list) {
                            Object object = map.get(name);
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
                        if(Utils.isBaseModel(fd)){
                            ls = buildModel(Utils.getModelClass(fd), list);
                        }else{
                            ls = getList(list, fd);
                        }
                    }else {
                        if(Utils.isBaseModel(fd)){
                            ls = buildModel(Utils.getModelClass(fd), list);
                        }else{
                            ls = getList(list, fd);
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

                        if(Utils.isBaseModel(fd)){
                            ls = buildModel(Utils.getModelClass(fd), list);
                        }else {
                            ls = getList(list, fd);
                        }
                        newInstance = new HashSet();
                        newInstance.addAll(ls);
                    }else {
                        if(Utils.isBaseModel(fd)){
                            ls = buildModel(Utils.getModelClass(fd), list);
                        }else {
                            ls = getList(list, fd);
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

    private static List getList(List<Map<String, Object>> list, Field fd) throws ClassNotFoundException {
        List ls;
        Class gClass = Utils.getGenericityClassOfCollect(fd);//泛型类型
        ls = new LinkedList();
        List finalLs = ls;
        list.forEach(map->{
           finalLs.add(convert.converterObject(gClass,map.get(fd.getName()),fd));
       });
        return ls;
    }

    /**
     *  string： mianModel 类名
     *  String ： 保存当前Model的父类名称，如果存在相同则提示Model存在相同父类。
     */
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
                throw new BaseException("Model 模型不能存在相同的父类，防止字段重复！！"+clazz.getName());
            }else{
                map.put(name,name);
            }
        }

    }




}
