package com.javaoffers.brief.modelhelper.pgsql;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.brief.modelhelper.utils.ColumnInfo;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create by cmj
 */
public class PostgreSqlInsertAllColValueCondition extends InsertAllColValueCondition {

    public PostgreSqlInsertAllColValueCondition(Class modelClass, Object model) {
        super(modelClass, model);
    }

    /**
     * INSERT INTO the_table (id, column_1, column_2)
     * VALUES (1, 'A', 'X'), (2, 'B', 'Y'), (3, 'C', 'Z')
     * ON CONFLICT (id) DO UPDATE
     *   SET column_1 = excluded.column_1,
     *       column_2 = excluded.column_2;
     */
    public void parseDupInsertSql(){

        TableInfo tableInfo = TableHelper.getTableInfo(this.getModelClass());
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        List<String> primaryColNameList = this.getParams().keySet()
                .stream().filter(primaryColNames::containsKey).collect(Collectors.toList());
        if(primaryColNameList.size() == 0){
            return;
        }

        StringBuilder onDuplicate = this.getOnDuplicate();
        onDuplicate.append(ConditionTag.ON_CONFLICT.getTag());
        onDuplicate.append(ConditionTag.LK.getTag());

        // SELECT 1001 AS order_number, 150 AS amount FROM dual
        primaryColNameList.forEach( uniqueCol->{
            Object value = this.getParams().get(uniqueCol);
            if(value instanceof String){
                onDuplicate.append(ConditionTag.QUOTATION.getTag());
                onDuplicate.append(value);
                onDuplicate.append(ConditionTag.QUOTATION.getTag());
            }else{
                onDuplicate.append(value);
            }
            onDuplicate.append(ConditionTag.AS.getTag());
            onDuplicate.append(uniqueCol);


            // 拼接 ON (tgt.order_number = src.order_number)
            onCondition.append("src");
            onCondition.append(ConditionTag.PERIOD.getTag()); // .
            onCondition.append(uniqueCol);

            onCondition.append(ConditionTag.EQ.getTag());

            onCondition.append(tableInfo.getTableName());
            onCondition.append(ConditionTag.PERIOD.getTag()); // .
            onCondition.append(uniqueCol);
        });
        onDuplicate.append(ConditionTag.RK.getTag());
        onDuplicate.append(ConditionTag.AS.getTag());
        onDuplicate.append("src ");

        onCondition.append(ConditionTag.RK.getTag()); // )
        onDuplicate.append(onCondition); // 拼接 on 条件

        //WHEN MATCHED THEN
        onDuplicate.append(ConditionTag.WHEN_MATCHED_THEN.getTag());
        onDuplicate.append(ConditionTag.UPDATE.getTag());
        onDuplicate.append(ConditionTag.SET.getTag());
        // update set tb.colName = #{colName}
        Set<Map.Entry<String, Object>> params = this.getParams().entrySet();
        ConditionTag comma = ConditionTag.BLANK;
        for(Map.Entry<String, Object> entry : params){
            String colName = entry.getKey();
            onDuplicate.append(comma.getTag());
            onDuplicate.append(tableInfo.getTableName());
            onDuplicate.append(ConditionTag.PERIOD.getTag());
            onDuplicate.append(colName);
            onDuplicate.append(ConditionTag.EQ.getTag());
            onDuplicate.append("#{");
            onDuplicate.append(colName);
            onDuplicate.append("}");
            if(comma == ConditionTag.BLANK){
                comma = ConditionTag.COMMA;
            }
        }

        //WHEN NOT MATCHED THEN
        onDuplicate.append(ConditionTag.WHEN_NOT_MATCHED_THEN.getTag());
        onDuplicate.append(ConditionTag.INSERT.getTag());
        onDuplicate.append(this.getSqlColNames());
        onDuplicate.append(ConditionTag.VALUES.getTag());
        onDuplicate.append(this.getSqlValues());

    }

    public void setParam(HashMap<String, Object> param) {
        super.getParams().clear();
        super.getParams().putAll(param);
    }
}
