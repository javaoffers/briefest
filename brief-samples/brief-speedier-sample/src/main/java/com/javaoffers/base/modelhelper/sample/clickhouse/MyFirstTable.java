package com.javaoffers.base.modelhelper.sample.clickhouse;

import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * https://clickhouse.com/docs/en/getting-started/quick-start
 * CREATE TABLE my_first_table
 * (
 *     user_id UInt32,
 *     message String,
 *     timestamp DateTime,
 *     metric Float32
 * )
 * ENGINE = MergeTree
 * PRIMARY KEY (user_id, timestamp)
 */
@BaseModel
@Data
public class MyFirstTable {
    @BaseUnique
    private Long userId;
    private String message;
    @BaseUnique
    private String timestamp;
    private Double metric;
}
