package com.javaoffers.brief.modelhelper.fun.condition.update;

import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.HeadCondition;
import com.javaoffers.brief.modelhelper.utils.Assert;
import com.javaoffers.brief.modelhelper.utils.ColumnInfo;
import com.javaoffers.brief.modelhelper.utils.DBType;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * all Col value update include null value
 *
 * @author create by cmj
 */
public class UpdateAllColValueCondition implements UpdateCondition {

    boolean isUpdateNull;

    boolean isExistsNoneNullValue;

    Class modelClass;

    Map<String, Object> params = new HashMap<>();

    Map<String, Object> npdateNullParams = new HashMap<>();

    Object model;

    private HeadCondition headCondition;

    private String tableName;

    //all Col
    private StringBuilder updateSqlNull = new StringBuilder(ConditionTag.UPDATE.getTag());

    // col not null only
    private StringBuilder updateSql = new StringBuilder(ConditionTag.UPDATE.getTag());

    @Override
    public ConditionTag getConditionTag() {
        return ConditionTag.UPDATE;
    }

    @Override
    public String getSql() {
        if (!isUpdateNull && !isExistsNoneNullValue) {
            return "";
        }
        return isUpdateNull ? updateSqlNull.toString() : updateSql.toString();
    }

    @Override
    public Map<String, Object> getParams() {
        return isUpdateNull ? params : npdateNullParams;
    }

    public UpdateAllColValueCondition(boolean isUpdateNull, Class modelClass, Object model) {
        Assert.isTrue(model != null, "model is not allowed to be null");
        this.isUpdateNull = isUpdateNull;
        this.modelClass = modelClass;
        this.model = model;

    }

    @Override
    public void setHeadCondition(HeadCondition headCondition) {
        this.headCondition = headCondition;
        this.tableName = TableHelper.getTableName(modelClass);
        TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
        Map<String, ColumnInfo> colNames = tableInfo.getColNames();
        Map<String, List<Field>> colAllAndFieldOnly = TableHelper.getOriginalColAllAndFieldOnly(modelClass);
        updateSqlNull.append(tableName);
        updateSql.append(tableName);
        DBType dbType = tableInfo.getDbType();
        String quote = dbType.getQuote();
        AtomicInteger status = new AtomicInteger(0);
        AtomicInteger statusNull = new AtomicInteger(0);
        colAllAndFieldOnly.forEach((cloName, fields) -> {
            try {
                Object oValue = null;
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    Object o = field.get(model);
                    //take the first non-null value
                    if (o != null) {
                        oValue = o;
                        break;
                    }
                }

                if (statusNull.get() == 0) {
                    updateSqlNull.append(ConditionTag.SET.getTag());
                    statusNull.incrementAndGet();
                } else {
                    updateSqlNull.append(", ");
                }

                String colNameTag = getNextTag();
                String expressionCloName = quote + cloName + quote;
                updateSqlNull.append(expressionCloName);
                updateSqlNull.append(" = #{");
                updateSqlNull.append(colNameTag);
                updateSqlNull.append("}");

                params.put(colNameTag, oValue);
                if (oValue != null && StringUtils.isNotBlank(oValue.toString())) {
                    npdateNullParams.put(colNameTag, oValue);
                    isExistsNoneNullValue = true;
                    if (status.get() == 0) {
                        updateSql.append(ConditionTag.SET.getTag());
                        status.incrementAndGet();
                    } else {
                        updateSql.append(", ");
                    }
                    updateSql.append(expressionCloName);
                    updateSql.append(" = #{");
                    updateSql.append(colNameTag);
                    updateSql.append("}");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public String getNextTag() {
        return this.headCondition.getNextTag();
    }
}
