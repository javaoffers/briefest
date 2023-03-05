package com.javaoffers.batis.modelhelper.encrypt.anno;

import java.lang.annotation.*;

/**
 * Encryption and decryption. Automatic decryption
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ColsEncrypt {

    /**
     *  encryption algorithm
     */
    Encrypt value();

    /**
     * The secret key
     */
    String key() ;

    /**
     * Support table fields, these table fields will be encrypted
     */
    String[] colNames();
}
