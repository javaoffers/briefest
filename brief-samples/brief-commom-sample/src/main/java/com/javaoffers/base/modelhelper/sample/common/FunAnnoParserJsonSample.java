package com.javaoffers.base.modelhelper.sample.common;

import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.anno.ColName;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonArray;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonContains;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonDepth;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonExtract;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonKeys;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonLength;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonObject;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonQuote;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonType;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonUnquote;
import com.javaoffers.brief.modelhelper.anno.fun.params.json.JsonValid;
import com.javaoffers.brief.modelhelper.anno.fun.parse.FunAnnoParser;
import com.javaoffers.brief.modelhelper.anno.fun.parse.ParseSqlFunResult;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Verifies that every JSON function annotation renders the expected SQL fragment.
 * Follows the same structure as {@link FunAnnoParserSample}.
 *
 * @author mingJie
 */
public class FunAnnoParserJsonSample {

    // --- extraction ---

    @ColName("extra_info")
    @JsonExtract("'$.nickName'")
    private String colName1; // JSON_EXTRACT(extra_info,'$.nickName')

    @ColName("extra_info")
    @JsonUnquote
    private String colName2; // JSON_UNQUOTE(extra_info)

    @ColName("extra_info")
    @JsonExtract("'$.nickName'")
    @JsonUnquote
    private String colName3; // JSON_UNQUOTE(JSON_EXTRACT(extra_info,'$.nickName'))

    @ColName("extra_info")
    @JsonExtract({"'$.nickName'", "'$.age'"})
    private String colName4; // JSON_EXTRACT(extra_info,'$.nickName','$.age')

    @ColName("name")
    @JsonQuote
    private String colName5; // JSON_QUOTE(name)

    // --- inspection ---

    @ColName("extra_info")
    @JsonLength
    private String colName6; // JSON_LENGTH(extra_info)

    @ColName("extra_info")
    @JsonLength("'$.tags'")
    private String colName7; // JSON_LENGTH(extra_info,'$.tags')

    @ColName("extra_info")
    @JsonKeys
    private String colName8; // JSON_KEYS(extra_info)

    @ColName("extra_info")
    @JsonKeys("'$.address'")
    private String colName9; // JSON_KEYS(extra_info,'$.address')

    @ColName("extra_info")
    @JsonType
    private String colName10; // JSON_TYPE(extra_info)

    @ColName("extra_info")
    @JsonValid
    private String colName11; // JSON_VALID(extra_info)

    @ColName("extra_info")
    @JsonDepth
    private String colName12; // JSON_DEPTH(extra_info)

    // --- containment ---

    @ColName("tags")
    @JsonContains("'java'")
    private String colName13; // JSON_CONTAINS(tags,'java')

    @ColName("extra_info")
    @JsonContains(value = "'java'", path = "'$.tags'")
    private String colName14; // JSON_CONTAINS(extra_info,'java','$.tags')

    // --- construction ---

    @ColName("name")
    @JsonObject(value = {"'name'"}, position = 1)
    private String colName15; // JSON_OBJECT('name',name)

    @ColName("tags")
    @JsonArray(value = {"'prefix'"}, position = 1)
    private String colName16; // JSON_ARRAY('prefix',tags)

    @ColName("name")
    @JsonArray
    private String colName17; // JSON_ARRAY(name)

    static TableInfo tableInfo;

    @Before
    public void before() {
        MockedStatic<TableHelper> mockedStatic = Mockito.mockStatic(TableHelper.class);
        tableInfo = new TableInfo("FunAnnoParserJsonSample") {
            public Map<String, String> getColNameOfModel() {
                HashMap<String, String> map = new HashMap<>();
                Field[] declaredFields = FunAnnoParserJsonSample.class.getDeclaredFields();
                for (Field field : declaredFields) {
                    map.put(field.getName(), field.getName());
                }
                return map;
            }
        };
        mockedStatic.when(() -> {
            TableHelper.getTableInfo(FunAnnoParserJsonSample.class);
        }).thenReturn(tableInfo);
    }

    @Test
    public void testJsonFunctions() throws Exception {
        assertSql(1, "JSON_EXTRACT(extra_info,'$.nickName')");
        assertSql(2, "JSON_UNQUOTE(extra_info)");
        assertSql(3, "JSON_UNQUOTE(JSON_EXTRACT(extra_info,'$.nickName'))");
        assertSql(4, "JSON_EXTRACT(extra_info,'$.nickName','$.age')");
        assertSql(5, "JSON_QUOTE(name)");
        assertSql(6, "JSON_LENGTH(extra_info)");
        assertSql(7, "JSON_LENGTH(extra_info,'$.tags')");
        assertSql(8, "JSON_KEYS(extra_info)");
        assertSql(9, "JSON_KEYS(extra_info,'$.address')");
        assertSql(10, "JSON_TYPE(extra_info)");
        assertSql(11, "JSON_VALID(extra_info)");
        assertSql(12, "JSON_DEPTH(extra_info)");
        assertSql(13, "JSON_CONTAINS(tags,'java')");
        assertSql(14, "JSON_CONTAINS(extra_info,'java','$.tags')");
        assertSql(15, "JSON_OBJECT('name',name)");
        assertSql(16, "JSON_ARRAY('prefix',tags)");
        assertSql(17, "JSON_ARRAY(name)");
    }

    private void assertSql(int n, String expected) throws Exception {
        String colName = "colName" + n;
        Class<FunAnnoParserJsonSample> modelClass = FunAnnoParserJsonSample.class;
        Field colField = modelClass.getDeclaredField(colName);
        ParseSqlFunResult parse = FunAnnoParser.parse(tableInfo, modelClass, colField, colName);
        LOGUtils.printLog(parse.getSqlFun());
        assertEquals("colName" + n + " SQL mismatch", expected, parse.getSqlFun());
        assertTrue("colName" + n + " should be flagged as a function", parse.isFun());
    }
}
