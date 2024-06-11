CREATE TABLE IF NOT EXISTS `user` (
  `id`   INTEGER PRIMARY KEY auto_increment,
  `name` CHARACTER VARYING(100),
  `birthday` DATETIME ,
  `create_time` DATETIME ,
  `money` CHARACTER VARYING(255) ,
  `sex` Integer ,
  `month` Integer,
  `work` CHARACTER VARYING(255),
  `is_del` Integer,
  `version` Integer
);


CREATE TABLE IF NOT EXISTS `user_order` (
  `id` INTEGER PRIMARY KEY auto_increment,
  `order_name` CHARACTER VARYING(255)  ,
  `order_money` Integer,
  `user_id` Integer,
  `is_del` Integer,
  `order_mark` CHARACTER VARYING(255) ,
  `create_time` datetime
) ;

CREATE TABLE IF NOT EXISTS `teacher` (
  `id`  INTEGER PRIMARY KEY auto_increment,
  `name` CHARACTER VARYING(255)  ,
  `status` Integer
) ;

CREATE TABLE IF NOT EXISTS `user_teacher` (
  `id` INTEGER PRIMARY KEY auto_increment,
  `user_id` Integer,
  `teacher_id` Integer

) ;

CREATE TABLE IF NOT EXISTS `encrypt_data` (
    `id`INTEGER PRIMARY KEY auto_increment,
    `encrypt_num` CHARACTER VARYING(512)

) ;

INSERT INTO `user` ( `name`, `birthday`, `create_time`, `money`, `sex`,`month`,`work` , `is_del`, `version`)
            VALUES ( 'ling', '2022-07-05 00:30:31', '2022-09-09 03:51:23','1000', '1','8', 'JAVA', '1', '1');

INSERT INTO user_order( `order_name`, `order_money`, `user_id`, `is_del`, `create_time`) VALUES ( 'computer', 101, 1, 1, '2022-09-12 03:51:32');
INSERT INTO user_order( `order_name`, `order_money`, `user_id`, `is_del`, `create_time`) VALUES ( 'phone', 120, 1, 1, '2022-09-11 03:51:36');

INSERT INTO teacher( `name`) VALUES ( 'mathTeacher:Amop');
INSERT INTO teacher( `name`) VALUES ( 'englishTeacher:Alen');

INSERT INTO user_teacher( `user_id`, `teacher_id`) VALUES ( 1, 1);
INSERT INTO user_teacher( `user_id`, `teacher_id`) VALUES ( 1, 2);


