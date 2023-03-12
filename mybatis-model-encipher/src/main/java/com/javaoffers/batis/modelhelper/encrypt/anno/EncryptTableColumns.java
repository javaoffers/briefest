package com.javaoffers.batis.modelhelper.encrypt.anno;

import java.lang.annotation.*;

/**
 * @author mingJie
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EncryptTableColumns {

    /**
     * 表名称
     * @return
     */
    String tableName();

    /**
     * 表字段名称
     * @return
     */
    String[] columns();
}
