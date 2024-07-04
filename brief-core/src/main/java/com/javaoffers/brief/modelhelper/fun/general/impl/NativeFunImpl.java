package com.javaoffers.brief.modelhelper.fun.general.impl;

import com.javaoffers.brief.modelhelper.core.BaseBrief;
import com.javaoffers.brief.modelhelper.core.BaseBriefImpl;
import com.javaoffers.brief.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.fun.ExecutFun;
import com.javaoffers.brief.modelhelper.fun.ExecutOneFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.utils.GsonUtils;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NativeFunImpl implements ExecutFun<String> {

    private String sqlText;

    private Map<String,Object> paramMap = Collections.emptyMap();

    private DataSource dataSource;

    private Class modelClass;

    public NativeFunImpl() {
        this.modelClass = CrudMapperMethodThreadLocal.getExcutorModel();
        this.dataSource = CrudMapperMethodThreadLocal.getExcutorDataSource();
    }

    public NativeFunImpl setSqlText(String sqlText) {
        this.sqlText = sqlText;
        return this;
    }

    public NativeFunImpl setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
        return this;
    }

    @Override
    public String ex() {
        List<String> exs = exs();
        if(exs.size() > 0){
            return exs.stream().collect(Collectors.joining("\t\n"));
        }
        return null;
    }

    @Override
    public List<String> exs() {
        if(StringUtils.isBlank(this.sqlText)){
            return Lists.newArrayList();
        }
        HeadCondition headCondition = new HeadCondition(this.dataSource, this.modelClass);
        BaseBrief instance = BaseBriefImpl.getInstance(headCondition);
        List list =  instance.queryData(sqlText,paramMap);
        return (List<String>) list.stream().map(el->{
            if(el instanceof List){
                List ls = (List)el;
                return ls.stream().map(Object::toString).collect(Collectors.joining("\t\n"));
            }
            return GsonUtils.gson.toJson(el);
        }).collect(Collectors.toList());
    }
}
