package com.javaoffers.brief.modelhelper.router;

import com.javaoffers.brief.modelhelper.parser.ColNameProcessorInfo;
import com.javaoffers.brief.modelhelper.parser.ConditionName;
import com.javaoffers.thrid.jsqlparser.schema.Column;
import com.javaoffers.thrid.jsqlparser.schema.Table;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

public class ShardingProcess {

    public static Consumer<ColNameProcessorInfo> RouterTableProcessor = colNameProcessorInfo->{
        Column column = colNameProcessorInfo.getColumn();
        ConditionName conditionName = colNameProcessorInfo.getConditionName();
        String columnName = column.getColumnName();
        if(ConditionName.SELECT_COL_NAME == conditionName || ConditionName.isWhereOnName(conditionName)){
            if(column.getTable() != null && StringUtils.isNotBlank(column.getTable().getName())){
                column.setColumnName("解密("+column.getTable().getName()+"."+columnName.toUpperCase()+")");
                column.setTable(new Table(""));
            }else{
                column.setColumnName("解密("+columnName.toUpperCase()+")");
            }

        } else if(ConditionName.VALUES == conditionName){
            column.setColumnName("加密("+columnName+")");
        } else if(ConditionName.UPDATE_SET == conditionName){
            column.setColumnName("加密("+columnName+")");
        }

    };
}
