package com.javaoffers.base.modelhelper.sample.sharding;

import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import com.javaoffers.brief.modelhelper.anno.fun.params.Left;

import com.javaoffers.brief.modelhelper.sharding.derive.ShardingStrategy;
import lombok.Data;

import java.util.Date;

@BaseModel("user")
@Data
public class ShardingUser {

    @BaseUnique
    private Long id;

    private String name;

    @ShardingStrategy(ShardingTableMonthStrategy.class)
    private Date birthday;
}
