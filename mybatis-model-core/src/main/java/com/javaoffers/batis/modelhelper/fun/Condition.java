package com.javaoffers.batis.modelhelper.fun;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/**
 * @Description: conditional excuse
 * @Auther: create by cmj on 2022/5/2 02:22
 */
public interface Condition {

    public ConditionTag getConditionTag();

    public String getSql();

    public Map<String, Object> getParams();

}
