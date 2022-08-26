CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `base`.`user`(`id`, `name`, `birthday`) VALUES (1, 'cmj', '2021-12-13 12:22:28');

CREATE TABLE `user_order` (
  `id` int(11) DEFAULT NULL,
  `order_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `order_money` int(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_del` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `base`.`user_order`(`id`, `order_name`, `order_money`, `user_id`, `is_del`) VALUES (1, 'computer', 100, 1, 1);
INSERT INTO `base`.`user_order`(`id`, `order_name`, `order_money`, `user_id`, `is_del`) VALUES (2, 'phone', 120, 1, 1);