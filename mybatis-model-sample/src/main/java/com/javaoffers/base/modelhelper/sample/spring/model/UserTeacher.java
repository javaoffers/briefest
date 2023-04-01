package com.javaoffers.base.modelhelper.sample.spring.model;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.anno.BaseUnique;
import lombok.Data;

/**
 * CREATE TABLE `user_teacher` (
 *   `id` int(11) NOT NULL AUTO_INCREMENT,
 *   `user_id` int(255) DEFAULT NULL,
 *   `teacher_id` int(255) DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 * @author mingJie
 */
@BaseModel
@Data
public class UserTeacher {
    @BaseUnique
    private Integer id;
    private Integer userId;
    private Integer teacherId;
}
