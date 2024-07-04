package com.javaoffers.brief.modelhelper.fun.general.impl;

import com.javaoffers.brief.modelhelper.core.BaseBrief;
import com.javaoffers.brief.modelhelper.core.BaseBriefImpl;
import com.javaoffers.brief.modelhelper.core.CrudMapperMethodThreadLocal;
import com.javaoffers.brief.modelhelper.core.Id;
import com.javaoffers.brief.modelhelper.fun.ExecutFun;
import com.javaoffers.brief.modelhelper.fun.ExecutOneFun;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NativeFunImpl implements ExecutFun<String> {

    private String sqlText;

    private List<Map<String,Object>> paramMap = Collections.emptyList();

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

    public NativeFunImpl setParamMap(List<Map<String, Object>> paramMap) {
        this.paramMap = paramMap;
        return this;
    }

    @Override
    public String ex() {
        List<String> exs = exs();
        if(exs.size() > 0){
            return exs.get(0);
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
        List<Id> list = instance.batchInsert(sqlText, this.paramMap);
        return list.stream().map(Id::toString).collect(Collectors.toList());
    }
}
