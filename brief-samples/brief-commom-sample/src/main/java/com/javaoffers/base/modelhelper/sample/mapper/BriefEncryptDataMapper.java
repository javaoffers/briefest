package com.javaoffers.base.modelhelper.sample.mapper;

import com.javaoffers.base.modelhelper.sample.model.EncryptData;
import com.javaoffers.brief.modelhelper.mapper.BriefMapper;

/**
 * @description:
 * CREATE TABLE `encrypt_data` (
 *   `id` int(11) NOT NULL AUTO_INCREMENT,
 *   `encrypt_num` varchar(512) COLLATE utf8_bin DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 * @author: create by cmj on 2022/10/15 00:55
 */
public interface BriefEncryptDataMapper extends BriefMapper<EncryptData> {
}
