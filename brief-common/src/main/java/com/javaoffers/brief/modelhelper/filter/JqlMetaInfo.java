package com.javaoffers.brief.modelhelper.filter;

import com.javaoffers.brief.modelhelper.core.BaseSQLStatement;
import com.javaoffers.brief.modelhelper.utils.SQLType;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @description:
 * @author: create by cmj on 2023/6/1 17:27
 */
public class JqlMetaInfo<T> {

    BaseSQLStatement sqlStatement;

    Consumer<T> consumer;

    SQLType sqlType;

    Operate operate;


    public JqlMetaInfo(BaseSQLStatement sqlStatement,Operate operate) {
        this.sqlStatement = sqlStatement;
        this.operate = operate;
    }

    public JqlMetaInfo(BaseSQLStatement sqlStatement, Consumer<T> consumer,Operate operate) {
        this.sqlStatement = sqlStatement;
        this.consumer = consumer;
        this.operate = operate;
    }

    public JqlMetaInfo(BaseSQLStatement sqlStatement, SQLType sqlType,Operate operate) {
        this.sqlStatement = sqlStatement;
        this.sqlType = sqlType;
        this.operate = operate;
    }

    public JqlMetaInfo(BaseSQLStatement sqlStatement, SQLType sqlType, Consumer<T> consumer,Operate operate) {
        this.sqlStatement = sqlStatement;
        this.consumer = consumer;
        this.sqlType = sqlType;
        this.operate = operate;
    }

    public  Map<String, Object> getParam() {
        List<Map<String, Object>> params = this.getParams();
        Map<String, Object> map = CollectionUtils.isNotEmpty(params) ? params.get(0) : Collections.emptyMap();
        return map;
    }

    public String getSql(){
        return this.sqlStatement.getSql();
    }

    public List<Map<String, Object>> getParams(){
        return this.sqlStatement.getParams();
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }

    public SQLType getSqlType() {
        return sqlType;
    }

    public BaseSQLStatement getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(BaseSQLStatement sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public void setConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    public void setSqlType(SQLType sqlType) {
        this.sqlType = sqlType;
    }

    public Operate getOperate() {
        return operate;
    }

    public enum Operate{
        QUERY,
        STREAM,
        INSERT,
        UPDATE,
        DELETE,
        NATIVE,
    }

}
