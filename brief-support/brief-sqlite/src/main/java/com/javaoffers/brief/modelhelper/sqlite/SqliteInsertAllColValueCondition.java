package com.javaoffers.brief.modelhelper.sqlite;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.condition.insert.InsertAllColValueCondition;
import com.javaoffers.brief.modelhelper.utils.ColumnInfo;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * create by cmj
 */
public class SqliteInsertAllColValueCondition extends InsertAllColValueCondition {

    public SqliteInsertAllColValueCondition(Class modelClass, Object model) {
        super(modelClass, model);
    }

    /**
     * INSERT INTO visits (ip, hits)
     * VALUES ('127.0.0.1', 1)
     * ON CONFLICT(ip) DO UPDATE SET hits = hits + 1;
     */
    public void parseDupInsertSql(){

        TableInfo tableInfo = TableHelper.getTableInfo(this.getModelClass());
        Map<String, ColumnInfo> primaryColNames = tableInfo.getPrimaryColNames();
        List<String> primaryColNameList = this.getParams().keySet()
                .stream().filter(primaryColNames::containsKey).collect(Collectors.toList());
        if(primaryColNameList.size() == 0){
            return;
        }

        //ON CONFLICT (id) DO UPDATE SET
        StringBuilder onDuplicate = this.getOnDuplicate();
        onDuplicate.append(ConditionTag.ON_CONFLICT.getTag());
        onDuplicate.append(ConditionTag.LK.getTag());
        onDuplicate.append(String.join(",", primaryColNameList));
        onDuplicate.append(ConditionTag.RK.getTag());
        onDuplicate.append(ConditionTag.DO.getTag());
        onDuplicate.append(ConditionTag.UPDATE.getTag());
        onDuplicate.append(ConditionTag.SET.getTag());

        //column_1 = excluded.column_1,
        String quote = tableInfo.getDbType().getQuote();
        Map<String, Object> params = getParams();
        AtomicReference<String> comma = new AtomicReference<>("");
        params.forEach((colName, value) -> {
            onDuplicate.append(comma.get());
            onDuplicate.append(quote);
            onDuplicate.append(colName);
            onDuplicate.append(quote);
            onDuplicate.append(ConditionTag.EQ.getTag());
            onDuplicate.append("#{");
            onDuplicate.append(colName);
            onDuplicate.append("}");
            comma.set(ConditionTag.COMMA.getTag());
        });


    }

    public void setParam(HashMap<String, Object> param) {
        super.getParams().clear();
        super.getParams().putAll(param);
    }
}
