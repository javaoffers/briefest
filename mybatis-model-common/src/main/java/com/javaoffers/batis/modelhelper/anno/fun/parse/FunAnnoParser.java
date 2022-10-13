package com.javaoffers.batis.modelhelper.anno.fun.parse;

import com.javaoffers.batis.modelhelper.anno.ColName;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.math.Rand;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.Curdate;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.CurrentDate;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.CurrentTime;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.Curtime;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.Now;
import com.javaoffers.batis.modelhelper.anno.fun.params.If;
import com.javaoffers.batis.modelhelper.anno.fun.params.IfEq;
import com.javaoffers.batis.modelhelper.anno.fun.params.IfGt;
import com.javaoffers.batis.modelhelper.anno.fun.params.IfGte;
import com.javaoffers.batis.modelhelper.anno.fun.params.IfLt;
import com.javaoffers.batis.modelhelper.anno.fun.params.IfLte;
import com.javaoffers.batis.modelhelper.anno.fun.params.IfNeq;
import com.javaoffers.batis.modelhelper.anno.fun.params.IfNotNull;
import com.javaoffers.batis.modelhelper.anno.fun.params.IfNull;
import com.javaoffers.batis.modelhelper.anno.fun.params.Left;
import com.javaoffers.batis.modelhelper.anno.fun.params.math.Abs;
import com.javaoffers.batis.modelhelper.anno.fun.params.math.Ceil;
import com.javaoffers.batis.modelhelper.anno.fun.params.math.Floor;
import com.javaoffers.batis.modelhelper.anno.fun.params.math.Mod;
import com.javaoffers.batis.modelhelper.anno.fun.params.math.Round;
import com.javaoffers.batis.modelhelper.anno.fun.params.math.Truncate;
import com.javaoffers.batis.modelhelper.anno.fun.params.time.Dayname;
import com.javaoffers.batis.modelhelper.anno.fun.params.time.Hour;
import com.javaoffers.batis.modelhelper.anno.fun.params.time.Minute;
import com.javaoffers.batis.modelhelper.anno.fun.params.time.Month;
import com.javaoffers.batis.modelhelper.anno.fun.params.time.Monthname;
import com.javaoffers.batis.modelhelper.anno.fun.params.time.Week;
import com.javaoffers.batis.modelhelper.anno.fun.params.time.Weekday;
import com.javaoffers.batis.modelhelper.anno.fun.params.time.Year;
import com.javaoffers.batis.modelhelper.anno.fun.params.varchar.CharLength;
import com.javaoffers.batis.modelhelper.anno.fun.params.varchar.Concat;
import com.javaoffers.batis.modelhelper.anno.fun.params.varchar.Length;
import com.javaoffers.batis.modelhelper.anno.fun.params.varchar.Lower;
import com.javaoffers.batis.modelhelper.anno.fun.params.varchar.Strcmp;
import com.javaoffers.batis.modelhelper.anno.fun.params.varchar.Upper;
import com.javaoffers.batis.modelhelper.constants.ModelHelpperConstants;
import com.javaoffers.batis.modelhelper.utils.TableHelper;
import com.javaoffers.batis.modelhelper.utils.TableInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author mingJie
 */
public class FunAnnoParser {

    public static final String DEFAULT_VALUE = "";

    public static String parse(Class modelClass, Field field){
        Assert.isTrue(field != null,"the field is null");
        Annotation[] allAnno = field.getDeclaredAnnotations();
        if(allAnno == null || allAnno.length == 0){
            return DEFAULT_VALUE;
        }
        //先查询出@ColName注解
        Appender appender = new Appender(modelClass,field);
        ColName colName = null;
        String coreSql = null;
        for(Annotation anno : allAnno){
            if(anno instanceof ColName){
                colName = (ColName) anno;
                continue;
            }
            //No reflection here is for performance
            parseParamCommon(appender, anno);
            parseParamVarchar(appender, anno);
            parseParamTime(appender, anno);
            parseParamMath(appender, anno);
            parseNoneParamTime(appender, anno);
            parseNoneParamMath(appender, anno);

        }
        if(colName != null){
            coreSql = colName.value();
            //a and b cannot be used together
            Assert.isTrue(colName != null && !appender.isNoneParam, "@ColName and" +
                    " "+appender.getNoParamFunName() + " cannot be used together");
        }

        return appender.toSqlString(coreSql);
    }

    private static void parseNoneParamMath(Appender appender, Annotation anno) {
        if(anno instanceof Rand){
            appender.appenderNoneParam(Rand.TAG);
        }
    }

    private static void parseNoneParamTime(Appender appender, Annotation anno) {
        if(anno instanceof Curdate){
            appender.appenderNoneParam(Curdate.TAG);
        }
        if(anno instanceof CurrentDate){
            appender.appenderNoneParam(CurrentDate.TAG);
        }
        if(anno instanceof CurrentTime){
            appender.appenderNoneParam(CurrentTime.TAG);
        }
        if(anno instanceof Curtime){
            appender.appenderNoneParam(Curtime.TAG);
        }
        if(anno instanceof Now){
            appender.appenderNoneParam(Now.TAG);
        }
    }

    private static void parseParamMath(Appender appender, Annotation anno) {
        if(anno instanceof Abs){
            Abs abs = (Abs) anno;
            appender.appender(abs.TAG);
        }
        if(anno instanceof Ceil){
            Ceil ceil = (Ceil) anno;
            appender.appender(ceil.TAG);
        }
        if(anno instanceof Floor){
            Floor floor = (Floor) anno;
            appender.appender(floor.TAG);
        }
        if(anno instanceof Mod){
            Mod mod = (Mod) anno;
            appender.appender(mod.TAG);
        }
        if(anno instanceof Round){
            Round round = (Round) anno;
            appender.appender(round.TAG,round.precision());
        }
        if(anno instanceof Truncate){
            Truncate truncate = (Truncate) anno;
            appender.appender(truncate.TAG, truncate.precision());
        }
    }

    private static void parseParamTime(Appender appender, Annotation anno) {
        
        if(anno instanceof Dayname){
            Dayname dayname = (Dayname) anno;
            appender.appender(dayname.TAG);
        }
        if(anno instanceof Hour){
            Hour hour = (Hour) anno;
            appender.appender(hour.TAG);
        }
        if(anno instanceof Minute){
            Minute minute = (Minute) anno;
            appender.appender(minute.TAG);
        }
        if(anno instanceof Month){
            Month month = (Month) anno;
            appender.appender(month.TAG);
        }
        if(anno instanceof Monthname){
            Monthname monthname = (Monthname) anno;
            appender.appender(monthname.TAG);
        }
        if(anno instanceof Week){
            Week week = (Week) anno;
            appender.appender(week.TAG);
        }
        if(anno instanceof Weekday){
            Weekday weekday = (Weekday) anno;
            appender.appender(weekday.TAG);
        }
        if(anno instanceof Year){
            Year year = (Year) anno;
            appender.appender(year.TAG);
        }
    }

    private static void parseParamVarchar(Appender appender, Annotation anno) {
        if(anno instanceof CharLength){
            CharLength charLength = ((CharLength) anno);
            appender.appender(charLength.TAG);
        }
        if(anno instanceof Concat){
            Concat concat = (Concat) anno;
            appender.appender(concat.TAG,concat.colNames());
        }
        if(anno instanceof Length){
            Length length = (Length) anno;
            appender.appender(length.TAG);
        }
        if(anno instanceof Lower){
            Lower lower = (Lower) anno;
            appender.appender(lower.TAG);
        }
        if(anno instanceof Strcmp){
            Strcmp strcmp = (Strcmp) anno;
            appender.appender(Strcmp.TAG,strcmp.expr());
        }
        if(anno instanceof Upper){
            Upper upper = (Upper) anno;
            appender.appender(upper.TAG);
        }
    }

    private static void parseParamCommon(Appender appender, Annotation anno) {
        if(anno instanceof Left){
            Left left = (Left) anno;
            int value = left.value();
            appender.appender(left.TAG, value);
        }
        if(anno instanceof IfNull){
            IfNull ifNull = (IfNull) anno;
            String value = ifNull.value();
            appender.appender(ifNull.TAG, value);
        }
        if(anno instanceof If){
            If if_ = (If) anno;
            String expr1 = if_.ep1();
            String expr2 = if_.ep2();
            appender.appender(if_.TAG, expr1, expr2);
        }
        if(anno instanceof IfEq){

        }
        if(anno instanceof IfGt){

        }
        if(anno instanceof IfGte){

        }
        if(anno instanceof IfLt){

        }
        if(anno instanceof IfLte){

        }
        if(anno instanceof IfNeq){

        }
        if(anno instanceof IfNotNull){

        }

    }

    static class Appender{
        private Class modelClass;
        private Field field;
        private String fieldName;
        private String colName;
        private Map<String, String> colNameOfModel;
        private StringBuilder appenderLeft = new StringBuilder();
        private StringBuilder appenderRight = new StringBuilder();
        private boolean isNoneParam = false;
        private String noParamFunName;
        //this param not colName.
        public Appender appender(String funName,Object... param){
            StringBuilder appenderLeftOut = new StringBuilder();
            appenderLeftOut.append(funName);
            appenderLeftOut.append("(");
            appenderLeftOut.append(this.appenderLeft);
            if(param != null){
                List<String> collect = Arrays.stream(param).filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList());
                if(collect!=null &&collect.size() > 0){
                    String join = String.join(",", collect);
                    appenderRight.append(",");
                    appenderRight.append(join);
                }
            }
            appenderRight.append(")");
            this.appenderLeft = appenderLeftOut;
            return this;
        }

        public Appender appenderNoneParam(String funName){
            Assert.isTrue(appenderLeft.length() == 0,
                    "The sql function without parameters is only allowed to appear" +
                    " at the top position when used.");
            appenderLeft.append(funName);
            appenderLeft.append("()");
            this.isNoneParam = true;
            this.noParamFunName = "@"+funName;
            return this;
        }

        public String toSqlString(String coreSql){
            if(this.isNoneParam){
                return appenderLeft.append(appenderRight).toString();
            }else{
                if(StringUtils.isBlank(coreSql)){
                    coreSql = colName;
                }
            }
            appenderLeft.append(coreSql);
            appenderLeft.append(appenderRight);
            return appenderLeft.toString();
        }


        public Appender(Class modelClass, Field field){
            this.modelClass = modelClass;
            TableInfo tableInfo = TableHelper.getTableInfo(modelClass);
            this.colNameOfModel = tableInfo.getColNameOfModel();
            this.field = field;
            this.fieldName = field.getName();
            this.colName = colNameOfModel.get(this.fieldName);
        }

        public boolean isNoneParam() {
            return isNoneParam;
        }

        public String getNoParamFunName(){
            return this.noParamFunName;
        }
    }
}
