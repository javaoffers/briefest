package com.javaoffers.batis.modelhelper.encrypt.anno.parse;

import com.javaoffers.batis.modelhelper.encrypt.ColNameProcessorInfo;
import com.javaoffers.batis.modelhelper.encrypt.ConditionName;
import com.javaoffers.batis.modelhelper.encrypt.SqlParserProcessor;
import net.sf.jsqlparser.schema.Column;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * create by cmj
 */
public class AesParser {

    private SqlParserProcessor.SqlParserProcessorBuild sqlParserProcessorBuild;

    private Map<String, String> kes = new HashMap<String,String>();

    // HEX(AES_ENCRYPT("原始字符串","密钥"));
    // AES_DECRYPT(UNHEX("加密后字符串"), "密钥");
    private Consumer<ColNameProcessorInfo> processor = colNameProcessorInfo ->{
        Column column = colNameProcessorInfo.getColumn();
        ConditionName conditionName = colNameProcessorInfo.getConditionName();
        String columnName = column.getColumnName();
        if(ConditionName.SELECT_COL_NAME == conditionName || ConditionName.isWhereOnName(conditionName)){
            String key = kes.get(colNameProcessorInfo.getTableName());
            column.setColumnName("AES_DECRYPT(UNHEX("+columnName+"), "+ key+")");
        }
    };

    public String parseSql(String sql){
        //TODO
        return "";
    }

    public AesParser(){
        sqlParserProcessorBuild = new SqlParserProcessor
                .SqlParserProcessorBuild();
    }

    public void addKey(String tableName, String key){
        kes.put(tableName, key);
    }

    public void addTableNameAndColName(String tableName, String colName){
        sqlParserProcessorBuild.addProcessor(tableName, processor);
        sqlParserProcessorBuild.addColName(tableName, colName);
    }
}
