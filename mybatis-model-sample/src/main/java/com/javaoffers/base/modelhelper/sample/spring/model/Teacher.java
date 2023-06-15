package com.javaoffers.base.modelhelper.sample.spring.model;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.anno.BaseUnique;
import com.javaoffers.batis.modelhelper.anno.derive.flag.RowStatus;
import lombok.Data;

/**
 * CREATE TABLE `teacher` (
 *   `id` int(11) NOT NULL AUTO_INCREMENT,
 *   `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 *   `status` int(2) DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 * @author mingJie
 */
@Data
@BaseModel
public class Teacher {
    @BaseUnique
    private Integer id;
    private String name;
    private RowStatus status;
}
