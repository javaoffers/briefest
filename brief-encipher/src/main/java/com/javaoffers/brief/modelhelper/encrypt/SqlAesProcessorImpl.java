package com.javaoffers.brief.modelhelper.encrypt;

import com.javaoffers.brief.modelhelper.exception.SqlAesProcessException;
import com.javaoffers.brief.modelhelper.parser.ColNameProcessorInfo;
import com.javaoffers.brief.modelhelper.parser.ConditionName;
import com.javaoffers.brief.modelhelper.parser.SqlParserProcessor;
import com.javaoffers.brief.modelhelper.parser.TableColumns;
import com.javaoffers.thrid.jsqlparser.schema.Column;
import com.javaoffers.thrid.jsqlparser.schema.Table;


import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * SQL field encryption processor. Who is either Aes128 using the encryption algorithm.
 * The class by {@code AesEncryptConfigBeanRegistrar} parsing is registered as a bean
 *
 * @author mingJie
 */

public class SqlAesProcessorImpl implements SqlAesProcessor{

    //Encrypted into hexadecimal, encryption prefix;
    private static final String ENCRYPT_PREFIX = "HEX(AES_ENCRYPT(";
    //Encryption suffix
    private static final String ENCRYPT_SUFFIX = "))";
    //Decryption prefix
    private static final String DECRYPT_PREFIX = "CAST(AES_DECRYPT(UNHEX(";
    //Decryption suffix
    private static final String DECRYPT_SUFFIX = ") as char)";
    //Aes private key. When the real to fill in hexadecimal (32);
    private String key;
    //The SQL parser
    private SqlParserProcessor sqlParserProcessor;
    //The field processing;
    private Consumer<ColNameProcessorInfo> processor = colNameProcessorInfo -> {
        Column column = colNameProcessorInfo.getColumn();
        ConditionName conditionName = colNameProcessorInfo.getConditionName();
        String columnName = column.getColumnName();
        //select / whereon
        if (ConditionName.SELECT_COL_NAME == conditionName || ConditionName.isWhereOnName(conditionName)) {
            if (column.getTable() != null) {
                column.setColumnName(DECRYPT_PREFIX + column.getTable().getName() + "." + columnName + ")," + "'" + key + "'" + DECRYPT_SUFFIX);
                //This table should be set to null, because above tableName has been filled into the columnName;
                column.setTable(new Table());
            } else {
                column.setColumnName(DECRYPT_PREFIX + columnName + ")," + "'" + key + "'" + DECRYPT_SUFFIX);
            }
        }
        // insert values/value.
        else if (ConditionName.VALUES == conditionName) {
            column.setColumnName(ENCRYPT_PREFIX + columnName + ", '" + key + "'" + ENCRYPT_SUFFIX);
        }
        //update set.
        else if (ConditionName.UPDATE_SET == conditionName) {
            column.setColumnName(ENCRYPT_PREFIX + columnName + ", '" + key + "'" + ENCRYPT_SUFFIX);
        }
    };

    /**
     * Parse SQL
     *
     * @param sql Raw SQL
     * @return The parsed SQL
     */
    public String parseSql(String sql)  {
        String tempSql = sql.toLowerCase();
        Set<String> needParseTables = sqlParserProcessor.getNeedParseTables();
        for (String tableName : needParseTables) {
            if (tempSql.contains(tableName.toLowerCase())) {
                try {
                    return sqlParserProcessor.parseSql(sql);
                }catch (Exception e){
                    e.printStackTrace();
                    throw new SqlAesProcessException(e.getMessage());
                }
            }
        }
        return sql;
    }

    public SqlAesProcessorImpl(String key, EncryptConfigContext configContext) {
        this.key = key;
        SqlParserProcessor.SqlParserProcessorBuild builder = SqlParserProcessor.builder();
        List<TableColumns> tableColums = configContext.getTableColumsByKey(key);
        for (TableColumns tableColumns : tableColums) {
            String tableName = tableColumns.getTableName();
            Set<String> columns = tableColumns.getColumns();
            builder.addProcessor(tableName, processor);
            for (String column : columns) {
                builder.addColName(tableName, column);
            }
        }
        this.sqlParserProcessor = builder.build();
    }

}
