package com.javaoffers.batis.modelhelper.encrypt;

import com.javaoffers.batis.modelhelper.encrypt.visitor.NamingStatementVisitorAdapter;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author cmj
 * @createTime 2023年03月04日 21:00:00
 */
public class SqlParserProcessor {

    private Map<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>> processor ;

    public void parseSql(String sqlStr) throws JSQLParserException {
        //创建content
        NamingContent namingContent = new NamingContent();
        namingContent.setProcessor(Collections.unmodifiableMap(processor));
        Statement statement = CCJSqlParserUtil.parse(sqlStr);
        //LOGUtils.printLog("start : "+statement.toString());

        NamingStatementVisitorAdapter statementVisitorAdapter = new NamingStatementVisitorAdapter(namingContent);
        statement.accept(statementVisitorAdapter);

        //LOGUtils.printLog("end : "+statement.toString());
    }

    public static SqlParserProcessorBuild builder(){
        return new SqlParserProcessorBuild();
    }

    public static class SqlParserProcessorBuild{
        //key: realTableName, Set: colNames, consumer: 用于处理colNames
        private HashMap<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>> processor = new HashMap<>();

        public SqlParserProcessorBuild addProcessor(String tableName, Consumer<ColNameProcessorInfo> columnProcessor){
            Pair<Set<String>, Consumer<ColNameProcessorInfo>> setConsumerPair = processor.get(tableName);
            if(setConsumerPair == null){
                setConsumerPair = Pair.of(new HashSet<>(), columnProcessor);
                processor.put(tableName, setConsumerPair);
            }

            return this;
        }

        public SqlParserProcessorBuild addColName(String tableName, String colName){
            Pair<Set<String>, Consumer<ColNameProcessorInfo>> columnProcessor = processor.get(tableName);
            Assert.isTrue(columnProcessor != null , "columnProcessor is null");
            columnProcessor.getLeft().add(colName);
            return this;
        }

        public SqlParserProcessor build(){
            SqlParserProcessor sqlParserProcessor = new SqlParserProcessor();
            sqlParserProcessor.processor = this.processor;
            return sqlParserProcessor;
        }

    }

}
