package com.javaoffers.base.modelhelper.sample.model;

import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import lombok.Data;

/**
 *SQL:
 * select table_x.id as table_x__id, table_x.age as table_x__age, table_x.name as table_x__name
 * from (select id, age, name from user) table_x  where  1=1
 */
@BaseModel(value = "table_x", fromView = "(select id, age, name from user)")
@Data
public class ViewModel {

    @BaseUnique
    private Integer id;
    private Integer age;
    private String name;
}
