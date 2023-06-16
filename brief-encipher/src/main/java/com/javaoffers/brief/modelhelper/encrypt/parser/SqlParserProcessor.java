package com.javaoffers.brief.modelhelper.encrypt.parser;
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
 * @createTime March 04, 2023 21:00:00
 */
public class SqlParserProcessor {

    static Logger log = LoggerFactory.getLogger(SqlParserProcessor.class);

    private static final String so = "`"; //symbol

    private Map<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>> processor;

    public String parseSql(String sqlStr) throws JSQLParserException {

        //Create content to store data information during processing
        NamingSelectContent namingContent = new NamingSelectContent();
        //set processor
        namingContent.setProcessor(Collections.unmodifiableMap(processor));
        //parsing sql
        Statement statement = CCJSqlParserUtil.parse(sqlStr);

        //log.info("start parse : {}", statement.toString());
        //Create sql analysis entry class
        NamingStatementVisitorAdapter statementVisitorAdapter = new NamingStatementVisitorAdapter(namingContent);
        //start parsing
        statement.accept(statementVisitorAdapter);

        //log.info("end parse : {}", statement.toString());

        return statement.toString();
    }

    /**
     * Get the table Name to be parsed
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
        //key: realTableName, Set: colNames, consumer: Used to process col Names
        private HashMap<String, Pair<Set<String>, Consumer<ColNameProcessorInfo>>> processor = new HashMap<>();

        /**
         * If the column Processor corresponding to the table Name already exists,
         * it will not be overwritten. It will not be added successfully.
         *
         * @param tableName       table name
         * @param columnProcessor The processor corresponding to the table name
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
         * Add colName corresponding to tableName, before executing this method,
         * make sure {@code addProcessor} tableName and columnProcessor already exist.
         *
         * @param tableName table name
         * @param colName   Field Name
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
         * Create a sql parsing processor
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
