package com.javaoffers.brief.modelhelper.fun;

import java.util.Map;

/**
 * @Description: conditional excuse
 * @Auther: create by cmj on 2022/5/2 02:22
 */
public interface Condition {

    public ConditionTag getConditionTag();

    public String getSql();

    public Map<String, Object> getParams();

}
