package com.javaoffers.batis.modelhelper.encrypt.anno;

import com.javaoffers.batis.modelhelper.encrypt.AesEncryptConfigBeanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author mingJie
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AesEncryptConfigBeanRegistrar.class)
public @interface AesEncryptConfig {

    /**
     * Private key
     * @return
     */
    String key();

    /**
     *
     * Block tableName and columns for encryption and decryption
     *
     * @return
     */
    EncryptTableColumns[] encryptTableColumns();

}
