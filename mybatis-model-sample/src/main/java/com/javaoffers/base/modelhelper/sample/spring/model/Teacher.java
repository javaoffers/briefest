package com.javaoffers.base.modelhelper.sample.spring.model;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.anno.BaseUnique;
import lombok.Data;

/**
 * CREATE TABLE `teacher` (
 *   `id` int(11) NOT NULL AUTO_INCREMENT,
 *   `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 * @author mingJie
 */
@Data
@BaseModel
public class Teacher {
    @BaseUnique
    private Integer id;
    private String name;
}
