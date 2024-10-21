package com.javaoffers.base.modelhelper.sample.sharding;

import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import com.javaoffers.brief.modelhelper.router.anno.ShardingStrategy;
import com.javaoffers.brief.modelhelper.router.strategy.ShardingTableEqStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@BaseModel("user")
@Data
public class ShardingUser {

    @BaseUnique
    private Long id;

    private String name;

    @ShardingStrategy(shardingTableStrategy=ShardingTableEqStrategy.class)
    private Date birthday;
}
