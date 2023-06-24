package com.javaoffers.brief.modelhelper.encrypt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * create by cmj.
 * Encryption configuration start
 */
public class BriefEncipher {

    private static EncryptConfigContext encryptConfigContext = new EncryptConfigContext();

    private static Set<String> keys = new HashSet<>();

    public static BriefEncipher getInstance(){
        return new BriefEncipher();
    }

    public synchronized BriefEncipher addEncryptConfig(String key, String tableName, String[] columns){
        this.encryptConfigContext.addEncryptConfig(key, tableName, columns);
        keys.add(key);
        return this;
    }

    public void initEncryptConfig(){
        List<SqlAesProcessorImpl> sqlAesProcessorList = new ArrayList<>();
        for(String key : keys){
            SqlAesProcessorImpl sqlAesProcessor = new SqlAesProcessorImpl(key, this.encryptConfigContext);
            sqlAesProcessorList.add(sqlAesProcessor);
        }
        new JqlAesInterceptorImpl().setSqlAesProcessors(sqlAesProcessorList);
    }
}
