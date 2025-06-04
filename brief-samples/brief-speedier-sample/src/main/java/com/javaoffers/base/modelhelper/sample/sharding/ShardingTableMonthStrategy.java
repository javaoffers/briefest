package com.javaoffers.base.modelhelper.sample.sharding;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingParams;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingTableStrategy;
import com.javaoffers.brief.modelhelper.utils.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/6/4
 */
public class ShardingTableMonthStrategy implements ShardingTableStrategy<Date> {

    @Override
    public String shardingExactly(ShardingParams<Date> shardingParams) {
        Date valueOne = shardingParams.getValueOne();
        return shardingParams.getTableName()+"_"+DateFormatUtils.format(valueOne, "yyyy-MM");
    }

    @Override
    public Set<String> shardingRange(ShardingParams<Date> shardingParams) {
        Set<String> sets = new LinkedHashSet<String>();
        List<Date> valueList = shardingParams.getValueList();
        for (int i = 0; i < valueList.size(); i++) {
            String st = shardingParams.getTableName()+"_"+DateFormatUtils.format(valueList.get(i), "yyyy-MM");
            sets.add(st);
        }
        return sets;
    }
}
