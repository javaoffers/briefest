package com.javaoffers.base.modelhelper.sample.common;

import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.batis.modelhelper.anno.ColName;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.math.Rand;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.Now;
import com.javaoffers.batis.modelhelper.anno.fun.params.IfNull;
import com.javaoffers.batis.modelhelper.anno.fun.params.Left;
import com.javaoffers.batis.modelhelper.anno.fun.params.varchar.Concat;
import com.javaoffers.batis.modelhelper.anno.fun.parse.FunAnnoParser;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import com.javaoffers.batis.modelhelper.utils.TableInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mingJie
 */
public class FunAnnoParserSample {
    @ColName("name")
    @Left(10)
    private String colName1;

    @ColName("name")
    @Left(10)
    @Concat(colNames = {"age"})
    private String colName2;


    @Left(10)
    @Concat(colNames = {"age"})
    private String colName3;

    @Now
    @Left(10)
    @Concat(colNames = {"age"})
    private String colName4;


    @Concat(colNames = {"age"})
    private String colName5;

    @Now
    @Concat(colNames = {"age"})
    @Left(10)
    private String colName6;


    @Concat(colNames = {"age"})
    @Left(10)
    private String colName7;

    @Now
    @Left(10)
    private String colName8;


    @Rand
    private String colName9;

    @Rand
    @ColName("name")
    private String colName10;

    @ColName("name")
    @IfNull("'Amop'")
    private String colName11;

    @Before
    public void before(){
        MockedStatic<TableHelper> mockedStatic = Mockito.mockStatic(TableHelper.class);
        TableInfo tableHelper = new TableInfo("FunAnnoParserSample"){
            public Map<String, String> getColNameOfModel() {
                HashMap<String, String> map = new HashMap<>();
                Field[] declaredFields = FunAnnoParserSample.class.getDeclaredFields();
                for(Field field: declaredFields){
                    map.put(field.getName(),field.getName());
                }
                return map;
            }
        };
        mockedStatic.when(()->{
            TableHelper.getTableInfo(FunAnnoParserSample.class);
        }).thenReturn(tableHelper);
    }

    @Test
    public void testColName() throws Exception{
        testColNameN(1);
        testColNameN(2);
        testColNameN(3);
        testColNameN(4);
        testColNameN(5);
        testColNameN(6);
        testColNameN(7);
        testColNameN(8);
        testColNameN(9);
        try {
            testColNameN(10);//throw error
        }catch (Exception e){
            e.printStackTrace();
        }
        testColNameN(11);


    }


    public void testColNameN(int n) throws Exception{
        Class<FunAnnoParserSample> modelClass = FunAnnoParserSample.class;
        Field colName = modelClass.getDeclaredField("colName"+n);
        String parse = FunAnnoParser.parse(modelClass, colName);
        LOGUtils.printLog(parse);
    }
}
