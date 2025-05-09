package com.javaoffers.brief.modelhelper.sharding.derive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc.
 *
 * @author cao ming jie create by 2025/5/2
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ShardingStrategy {
    /**
     * 指定sharding strategic
     * @return
     */
    Class<? extends ShardingTableStrategy> value() default ShardingTableStrategy.class;
}
