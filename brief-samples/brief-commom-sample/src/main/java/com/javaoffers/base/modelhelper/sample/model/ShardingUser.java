package com.javaoffers.base.modelhelper.sample.model;

import com.javaoffers.base.modelhelper.sample.sharding.ShardingUserTableMonthStrategy;
import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;

import com.javaoffers.brief.modelhelper.sharding.derive.ShardingStrategy;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingTableMonthStrategy;
import lombok.Data;

import java.util.Date;

@BaseModel("sharding_user")
@Data
public class ShardingUser {

    @BaseUnique
    private Long id;

    private String name;

    @ShardingStrategy(ShardingUserTableMonthStrategy.class)
    private Date birthday;
}
