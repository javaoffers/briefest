package com.javaoffers.base.modelhelper.sample.model;

import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;

import com.javaoffers.brief.modelhelper.sharding.derive.ShardingStrategy;
import com.javaoffers.brief.modelhelper.sharding.derive.ShardingTableMonthStrategy;
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
