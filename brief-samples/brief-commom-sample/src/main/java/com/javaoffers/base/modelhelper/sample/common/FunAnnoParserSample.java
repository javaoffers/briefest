package com.javaoffers.base.modelhelper.sample.common;

import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.brief.modelhelper.anno.ColName;
import com.javaoffers.brief.modelhelper.anno.fun.noneparam.math.Rand;
import com.javaoffers.brief.modelhelper.anno.fun.noneparam.time.Now;
import com.javaoffers.brief.modelhelper.anno.fun.params.CaseWhen;
import com.javaoffers.brief.modelhelper.anno.fun.params.If;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfEq;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfGt;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfNotNull;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfNull;
import com.javaoffers.brief.modelhelper.anno.fun.params.Left;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Concat;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.ConcatWs;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.GroupConcat;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Trim;
import com.javaoffers.brief.modelhelper.anno.fun.parse.FunAnnoParser;
import com.javaoffers.brief.modelhelper.anno.fun.parse.ParseSqlFunResult;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mingJie
 */
public class FunAnnoParserSample {
    @ColName("name")
    @Left(10)
    private String colName1; //LEFT(name,10)

    @ColName("name")
    @Left(10)
    @Concat( {"age"})
    @Resource
    private String colName2; //CONCAT(LEFT(name,10),age)


    @Left(10)
    @Concat( {"age"})
    private String colName3;//CONCAT(LEFT(colName3,10),age)

    @Now
    @Left(10)
    @Concat( {"age"})
    private String colName4;//CONCAT(LEFT(NOW(),10),age)


    @Concat( {"age"})
    private String colName5;//CONCAT(colName5,age)

    @Now
    @Concat({"age"})
    @Left(10)
    private String colName6;//LEFT(CONCAT(NOW(),age),10)


    @Concat({"age"})
    @Left(10)
    private String colName7;//LEFT(CONCAT(colName7,age),10)

    @Now
    @Left(10)
    private String colName8;//LEFT(NOW(),10)


    @Rand
    private String colName9;//RAND()

    @Rand
    @ColName("name")
    private String colName10;//java.lang.IllegalArgumentException: @ColName and @RAND cannot be used together

    @ColName("name")
    @IfNull("'Amop'")
    private String colName11;//IFNULL(name,'Amop')

    @ColName("sex = 1")
    @If(ep1 = "'boy'",ep2 = "'girl'")
    private String colName12;//IF(sex = 1,'boy','girl')

    /**
     * select if(1,'1','0') output 1
     * select if(0,'1','0') output 0
     */
    @ColName("sex")
    @IfNull("1")
    @If(ep1 = "'boy'", ep2 = "'girl'")
    private String colName13;// IF(IFNULL(sex,1),'boy','girl')

    @ColName("sex")
    @IfEq(eq = "1",ep1 = "'boy'", ep2 = "'girl'")
    private String colName14; //IF(sex = 1,'boy','girl')

    @ColName("money")
    @IfNotNull("'rich'")
    private String colName15; // IF(money is not null ,'rich',null)

    @ColName("money")
    @IfNotNull(value = "'rich'",ifNull = "'poor'")
    private String colName16; //IF(money is not null ,'rich','poor')

    @ColName("money")
    @IfNotNull(value = "'rich'",ifNull = "'poor'")
    @IfEq(eq = "'rich'",ep1 = "'i want to marry him'", ep2 = "'i want to break up with him'")
    private String colName17; //IF(IF(money is not null ,'rich','poor') = 'rich','i want to marry him','i want to break up with him')

    @ColName("money")
    @IfGt(gt = "100000",ep1 = "'rich'", ep2 = "'poor'")
    @IfEq(eq = "'rich'",ep1 = "'i want to marry him'", ep2 = "'i want to break up with him'")
    private String colName18; //IF(IF(money > 100000,'rich','poor') = 'rich','i want to marry him','i want to break up with him')

    @ColName("name")
    @Trim
    private String colName19; //TRIM(name)

    @ColName("name")
    @Concat(value = "'hello'", position = -1)
    private String colName20;//CONCAT('hello',name)

    @ColName("name")
    @Concat(value = "'hello'", position = 1)
    private String colName21; //CONCAT('hello',name)

    @ColName("name")
    @Concat(value = {"'hello'"," 'how are you?' "}, position = 1)
    private String colName22;//  CONCAT('hello',name, 'how are you?' )

    @ColName("name")
    @GroupConcat
    private String colName23;//GROUP_CONCAT( name )

    @ColName("name")
    @GroupConcat(distinct = true)
    private String colName24;//GROUP_CONCAT( distinct name )

    @ColName("name")
    @GroupConcat(distinct = true, orderBy = @GroupConcat.OrderBy(colName = "age",sort = GroupConcat.Sort.ASC) )
    private String colName25;//GROUP_CONCAT( distinct name  order by age ASC)

    @ColName("name")
    @GroupConcat(distinct = true, orderBy = @GroupConcat.OrderBy(colName = "age",sort = GroupConcat.Sort.DESC) ,separator = "-")
    private String colName26;//GROUP_CONCAT( distinct name  order by age DESC separator '-')

    @ColName("name")
    @Concat("age")
    @GroupConcat(distinct = true, orderBy = @GroupConcat.OrderBy(colName = "age",sort = GroupConcat.Sort.DESC) ,separator = "-")
    private String colName27;//GROUP_CONCAT( distinct CONCAT(name,age)  order by age DESC separator '-')

    @ColName("name")
    @ConcatWs(separator = " '-' ", value = "age")
    private String colName28;

    @ColName("name")
    @ConcatWs(separator = " '-' ", value = {"age","'hello'"}, position = 1)
    private String colName29;

    @CaseWhen(whens = {
            @CaseWhen.When(when = "score > 80", then = "'Grand'"),
            @CaseWhen.When(when = "score < 80 and score > 50", then = "'General'"),
            @CaseWhen.When(when = "score < 50 and score > 10", then = "'noGood'"),
    }, elseEnd = @CaseWhen.Else("'VeryBad'"))
    private String scoreDescription;

    @CaseWhen(whens = {
            @CaseWhen.When(when = "score > 80", then = "'Grand'"),
            @CaseWhen.When(when = "score < 80 and score > 50", then = "'General'"),
            @CaseWhen.When(when = "score < 50 and score > 10", then = "'noGood'"),
    }, elseEnd = @CaseWhen.Else("'VeryBad'"))
    @Left(10)
    private String scoreDescription2;


    static TableInfo tableInfo;
    @Before
    public void before(){
        MockedStatic<TableHelper> mockedStatic = Mockito.mockStatic(TableHelper.class);
        tableInfo = new TableInfo("FunAnnoParserSample"){
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
        }).thenReturn(tableInfo);
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
        testColNameN(12);
        testColNameN(13);
        testColNameN(14);
        testColNameN(15);
        testColNameN(16);
        testColNameN(17);
        testColNameN(18);
        testColNameN(19);
        testColNameN(20);
        testColNameN(21);
        testColNameN(22);
        testColNameN(23);
        testColNameN(24);
        testColNameN(25);
        testColNameN(26);
        testColNameN(27);
        testColNameN(28);
        testColNameN(29);
        testColNameN("scoreDescription");
        testColNameN("scoreDescription2");
    }


    public void testColNameN(int n) throws Exception{
        Class<FunAnnoParserSample> modelClass = FunAnnoParserSample.class;
        String colName = "colName"+n;
        Field colField = modelClass.getDeclaredField(colName);
        ParseSqlFunResult parse = FunAnnoParser.parse(tableInfo,modelClass, colField, colName);
        LOGUtils.printLog(parse.getSqlFun());
    }

    public void testColNameN( String colName) throws Exception{
        Class<FunAnnoParserSample> modelClass = FunAnnoParserSample.class;
        Field colField = modelClass.getDeclaredField(colName);
        ParseSqlFunResult parse = FunAnnoParser.parse(tableInfo,modelClass, colField, colName);
        LOGUtils.printLog(parse.getSqlFun());
    }
}
