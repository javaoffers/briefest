package com.javaoffers.batis.modelhelper.encrypt.parser;
import com.javaoffers.thrid.jsqlparser.parser.CCJSqlParserUtil;
import com.javaoffers.thrid.jsqlparser.statement.Statement;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import com.javaoffers.thrid.jsqlparser.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author cmj
 * @createTime 2023年03月04日 21:00:00
 */
public class SqlParserProcessor {

    //static Logger log = LoggerFactory.getLogger(SqlParserProcessor.class);

    private static final String so = "`"; //symbol

    private Map<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>> processor;

    public String parseSql(String sqlStr) throws JSQLParserException {

        //创建content,储存处理过程中的数据信息
        NamingSelectContent namingContent = new NamingSelectContent();
        //设置处理器
        namingContent.setProcessor(Collections.unmodifiableMap(processor));
        //解析sql
        Statement statement = CCJSqlParserUtil.parse(sqlStr);

        //log.info("start parse : {}", statement.toString());
        //创建sql解析入口类
        NamingStatementVisitorAdapter statementVisitorAdapter = new NamingStatementVisitorAdapter(namingContent);
        //开始解析
        statement.accept(statementVisitorAdapter);

        //log.info("end parse : {}", statement.toString());

        return statement.toString();
    }

    /**
     * 获取要解析的tableName
     *
     * @return
     */
    public Set<String> getNeedParseTables() {
        return processor.keySet();
    }

    public static SqlParserProcessorBuild builder() {
        return new SqlParserProcessorBuild();
    }

    public static class SqlParserProcessorBuild {
        //key: realTableName, Set: colNames, consumer: 用于处理colNames
        private HashMap<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>> processor = new HashMap<>();

        /**
         * 如果tableName对应的columnProcessor已经存在则不会进行覆盖.也不会新增成功.
         *
         * @param tableName       表名称
         * @param columnProcessor 表名对应的处理器
         * @return this
         */
        public SqlParserProcessorBuild addProcessor(String tableName, Consumer<ColNameProcessorInfo> columnProcessor){
            offerProcessor(tableName, columnProcessor);
            offerProcessor(makeSo(tableName), columnProcessor);
            return this;
        }

        private SqlParserProcessorBuild offerProcessor(String tableName, Consumer<ColNameProcessorInfo> columnProcessor){
            Pair<Set<String>, Consumer<ColNameProcessorInfo>> setConsumerPair = processor.get(tableName);
            if(setConsumerPair == null){
                setConsumerPair = Pair.of(new HashSet<>(), columnProcessor);
                processor.put(tableName, setConsumerPair);
            }

            return this;
        }

        /**
         * 添加tableName对应的colName, 执行此方法前要确保{@code addProcessor} tableName和columnProcessor已经存在.
         *
         * @param tableName 表名称
         * @param colName   字段名称
         * @return this
         */
        public SqlParserProcessorBuild addColName(String tableName, String colName){
            offerColName(tableName, colName);
            offerColName(makeSo(tableName), colName);
            return this;
        }

        private SqlParserProcessorBuild offerColName(String tableName, String colName){
            Pair<Set<String>, Consumer<ColNameProcessorInfo>> columnProcessor = processor.get(tableName);
            Assert.isTrue(columnProcessor != null , "columnProcessor is null");
            columnProcessor.getLeft().add(colName);
            columnProcessor.getLeft().add(makeSo(colName)); //支持 `colName` 格式
            return this;
        }

        public String makeSo(String name){
            if(name.contains(so)){
                return name;
            }
            return so + name + so;
        }

        /**
         * 创建一个sql解析处理器
         *
         * @return sqlParserProcessor
         */
        public SqlParserProcessor build() {
            SqlParserProcessor sqlParserProcessor = new SqlParserProcessor();
            sqlParserProcessor.processor = this.processor;
            //log.info("build : {} ", this.processor);
            return sqlParserProcessor;
        }
    }
}
