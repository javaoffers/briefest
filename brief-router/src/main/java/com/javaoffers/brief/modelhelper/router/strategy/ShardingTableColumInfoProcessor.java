package com.javaoffers.brief.modelhelper.router.strategy;

import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveInfo;
import com.javaoffers.brief.modelhelper.core.BaseSQLInfo;
import com.javaoffers.brief.modelhelper.parser.SqlParserProcessor;
import com.javaoffers.thrid.jsqlparser.JSQLParserException;

import java.lang.reflect.Field;
import java.util.List;

public class ShardingTableColumInfoProcessor extends DeriveInfo {

    private SqlParserProcessor sqlParserProcessor;
    private ThreadLocal<ShardingSQLContext> task = new ThreadLocal();

    public ShardingTableColumInfoProcessor(String colName, Field field, SqlParserProcessor sqlParserProcessor) {
        super(colName, field);
        this.sqlParserProcessor = sqlParserProcessor;

    }

    public List<BaseSQLInfo> shardingParse(BaseSQLInfo sqlInfo){
        String sql = sqlInfo.getSql();
        try {
            ShardingSQLContext shardingSQLContext = new ShardingSQLContext(sqlInfo);
            task.set(shardingSQLContext);
            sqlParserProcessor.parseSql(sql);
            List<BaseSQLInfo> sharding = task.get().getShardingSQLInfos();;
            return sharding;
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }finally {
            task.remove();
        }
    }

    public ShardingSQLContext getShardingSQLContext(){
        return  task.get();
    }

}
