CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `money` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sex` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `user` VALUES (1, 'ling', '2022-07-05 00:30:31', '2022-09-09 03:51:23');

CREATE TABLE `user_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `order_money` int(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_del` int(3) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;;

INSERT INTO `base`.`user_order`(`id`, `order_name`, `order_money`, `user_id`, `is_del`, `create_time`) VALUES (1, 'computer', 101, 1, 1, '2022-09-12 03:51:32');
INSERT INTO `base`.`user_order`(`id`, `order_name`, `order_money`, `user_id`, `is_del`, `create_time`) VALUES (2, 'phone', 120, 1, 1, '2022-09-11 03:51:36');

CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `base`.`teacher`(`id`, `name`) VALUES (1, 'mathTeacher:Amop');
INSERT INTO `base`.`teacher`(`id`, `name`) VALUES (2, 'englishTeacher:Alen');

CREATE TABLE `user_teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(255) DEFAULT NULL,
  `teacher_id` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `base`.`user_teacher`(`id`, `user_id`, `teacher_id`) VALUES (1, 1, 1);
INSERT INTO `base`.`user_teacher`(`id`, `user_id`, `teacher_id`) VALUES (2, 1, 2);