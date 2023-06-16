package com.javaoffers.brief.modelhelper.anno.fun.parse;

import com.javaoffers.brief.modelhelper.anno.ColName;
import com.javaoffers.brief.modelhelper.anno.fun.noneparam.math.Rand;
import com.javaoffers.brief.modelhelper.anno.fun.noneparam.time.Curdate;
import com.javaoffers.brief.modelhelper.anno.fun.noneparam.time.CurrentDate;
import com.javaoffers.brief.modelhelper.anno.fun.noneparam.time.CurrentTime;
import com.javaoffers.brief.modelhelper.anno.fun.noneparam.time.Curtime;
import com.javaoffers.brief.modelhelper.anno.fun.noneparam.time.Now;
import com.javaoffers.brief.modelhelper.anno.fun.noneparam.time.UnixTimestamp;
import com.javaoffers.brief.modelhelper.anno.fun.params.CaseWhen;
import com.javaoffers.brief.modelhelper.anno.fun.params.If;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfEq;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfGt;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfGte;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfLt;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfLte;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfNeq;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfNotNull;
import com.javaoffers.brief.modelhelper.anno.fun.params.IfNull;
import com.javaoffers.brief.modelhelper.anno.fun.params.Left;
import com.javaoffers.brief.modelhelper.anno.fun.params.Right;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Abs;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Ceil;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Floor;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Mod;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Round;
import com.javaoffers.brief.modelhelper.anno.fun.params.math.Truncate;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.DateFormat;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Dayname;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.FromDays;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Hour;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Makedate;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Maketime;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Minute;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Month;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Monthname;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.SecToTime;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.StrToDate;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.TimeFormat;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.TimeToSec;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.ToDays;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Week;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Weekday;
import com.javaoffers.brief.modelhelper.anno.fun.params.time.Year;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.CharLength;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Concat;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.ConcatWs;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.GroupConcat;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.LTrim;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Length;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Lower;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.RTrim;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Repeat;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Replace;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Strcmp;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.SubString;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Trim;
import com.javaoffers.brief.modelhelper.anno.fun.params.varchar.Upper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author mingJie
 */
public class FunAnnoParser {


    public static final String DEFAULT_VALUE = "";

    public static ParseSqlFunResult parse(TableInfo tableInfo, Class modelClass, Field field, String defaultColName){

        Assert.isTrue(field != null,"the field is null");
        Annotation[] allAnno = field.getDeclaredAnnotations();
        if(allAnno == null || allAnno.length == 0){
            return null;
        }
        //先查询出@ColName注解
        Appender appender = new Appender(modelClass,field,defaultColName);
        ColName colNameAnno = null;
        String coreSql = null;
        boolean isFunSql = false;
        boolean isExcludeColAll = false;
        CaseWhen caseWhen = null;
        for(Annotation anno : allAnno){
            if(anno instanceof ColName){
                colNameAnno = (ColName) anno;
                isExcludeColAll = colNameAnno.excludeColAll();
                continue;
            }else if(anno instanceof GroupConcat){
                isExcludeColAll = ((GroupConcat) anno).excludeColAll();
            }else if(anno instanceof CaseWhen){
                caseWhen = (CaseWhen)anno;
                isFunSql = true;
                continue;
            }
            //This will be optimized for strategy mode later. to avoid a lot of if statements
            isFunSql =
            parseParamCommon(appender, anno)||
            parseParamVarchar(appender, anno)||
            parseParamTime(appender, anno)||
            parseParamMath(appender, anno)||
            parseNoneParamTime(appender, anno)||
            parseNoneParamMath(appender, anno) || isFunSql;

        }
        //case when check. It is not allowed to have other Sql Functions or @ColName at the same time
        if(caseWhen != null){
            Assert.isTrue(colNameAnno == null,
                    "@CaseWhen and @ColName cannot be used together ");
            Assert.isTrue(!appender.isNoneParam,
                    "@CaseWhen and" +
                            " "+appender.getNoParamFunName() + " cannot be used together");

            CaseWhen.When[] whens = caseWhen.whens();
            StringBuilder caseWhenStr = new StringBuilder(CaseWhen.CASE);
            for(CaseWhen.When when : whens){
                    String whenExp = when.when();
                    String then = when.then();
                    caseWhenStr.append(CaseWhen.WHEN);
                    caseWhenStr.append(whenExp);
                    caseWhenStr.append(CaseWhen.THEN);
                    caseWhenStr.append(then);
            }
            CaseWhen.Else elseEnd = caseWhen.elseEnd();
            caseWhenStr.append(CaseWhen.ELSE);
            caseWhenStr.append(elseEnd.value());
            caseWhenStr.append(CaseWhen.END);
            coreSql = caseWhenStr.toString();
        }
        if(colNameAnno != null) {
            coreSql = colNameAnno.value();
            //a and b cannot be used together
            Assert.isTrue(colNameAnno != null && !appender.isNoneParam, "@ColName and" +
                    " "+appender.getNoParamFunName() + " cannot be used together");
        }

        if(isFunSql && tableInfo.getColNames().containsKey(coreSql)){
            coreSql = tableInfo.getTableName()+"."+coreSql;
        }
        String finishSql = appender.toSqlString(coreSql);
        // only one @ColName(" 'xxx' ") or @ColName( funExp ) on field
        if(!isFunSql && !tableInfo.getColNames().containsKey(finishSql)){
            isFunSql = true;
        }

        return new ParseSqlFunResult(finishSql, isFunSql, isExcludeColAll);
    }

    private static boolean parseNoneParamMath(Appender appender, Annotation anno) {
        boolean status = false;
        if(anno instanceof Rand){
            appender.appenderNoneParam(Rand.TAG);
            status = true;
        }
        return status;
    }

    private static boolean parseNoneParamTime(Appender appender, Annotation anno) {
        boolean status = false;
        if(anno instanceof CurrentDate){
            appender.appenderNoneParam(CurrentDate.TAG);
            status = true;
        }
        else if(anno instanceof Curdate){
            appender.appenderNoneParam(Curdate.TAG);
            status = true;
        }
        else if(anno instanceof CurrentTime){
            appender.appenderNoneParam(CurrentTime.TAG);
            status = true;
        }
        else if(anno instanceof Curtime){
            appender.appenderNoneParam(Curtime.TAG);
            status = true;
        }
        else if(anno instanceof Now){
            appender.appenderNoneParam(Now.TAG);
            status = true;
        }
        else if(anno instanceof UnixTimestamp){
            appender.appender(UnixTimestamp.TAG);
            status = true;
        }
        return status;
    }

    private static boolean parseParamMath(Appender appender, Annotation anno) {
        boolean status = false;
        if(anno instanceof Abs){
            Abs abs = (Abs) anno;
            appender.appender(abs.TAG);
            status = true;
        }
        else if(anno instanceof Ceil){
            Ceil ceil = (Ceil) anno;
            appender.appender(ceil.TAG);
            status = true;
        }
        else if(anno instanceof Floor){
            Floor floor = (Floor) anno;
            appender.appender(floor.TAG);
            status = true;
        }
        else if(anno instanceof Mod){
            Mod mod = (Mod) anno;
            int value = mod.value();
            appender.appender(mod.TAG, value);
            status = true;
        }
        else if(anno instanceof Round){
            Round round = (Round) anno;
            appender.appender(round.TAG,round.precision());
            status = true;
        }
        else if(anno instanceof Truncate){
            Truncate truncate = (Truncate) anno;
            appender.appender(truncate.TAG, truncate.value());
            status = true;
        }
        return status;
    }

    private static boolean parseParamTime(Appender appender, Annotation anno) {
        boolean status = false;

         if(anno instanceof DateFormat){
            DateFormat dateFormat = (DateFormat)anno;
            String format = dateFormat.value();
            appender.appender(dateFormat.TAG, format);
            status = true;
        }else if(anno instanceof Dayname){
            Dayname dayname = (Dayname) anno;
            appender.appender(dayname.TAG);
            status = true;
        }
        else if(anno instanceof FromDays){
            appender.appender(FromDays.TAG);
            status = true;
        }
        else if(anno instanceof Hour){
            Hour hour = (Hour) anno;
            appender.appender(hour.TAG);
            status = true;
        }
        else if(anno instanceof Maketime){
            int ep1 = ((Maketime) anno).ep1();
            int ep2 = ((Maketime) anno).ep2();
            appender.appender(Maketime.TAG, ep1, ep2);
            status = true;
        }
        else if(anno instanceof Makedate){
            int value = ((Makedate) anno).value();
            appender.appender(Makedate.TAG, value);
            status = true;
        }
        else if(anno instanceof Minute){
            Minute minute = (Minute) anno;
            appender.appender(minute.TAG);
            status = true;
        }
        else if(anno instanceof Month){
            Month month = (Month) anno;
            appender.appender(month.TAG);
            status = true;
        }
        else if(anno instanceof Monthname){
            Monthname monthname = (Monthname) anno;
            appender.appender(monthname.TAG);
            status = true;
        }
        else if(anno instanceof SecToTime){
            appender.appender(SecToTime.TAG);
            status = true;
        }
        else if(anno instanceof StrToDate){
            String format = ((StrToDate) anno).value();
            appender.appender(StrToDate.TAG, format);
            status = true;
        }
        else if(anno instanceof TimeFormat){
            TimeFormat timeFormat = (TimeFormat) anno;
            String format = timeFormat.value();
            appender.appender(timeFormat.TAG, format);
            status = true;
        }
        else if(anno instanceof ToDays){
            appender.appender(ToDays.TAG);
            status = true;
        }
        else if(anno instanceof TimeToSec){
            appender.appender(TimeToSec.TAG);
            status = true;
        }
        else if(anno instanceof Week){
            Week week = (Week) anno;
            appender.appender(week.TAG);
            status = true;
        }
        else  if(anno instanceof Weekday){
            Weekday weekday = (Weekday) anno;
            appender.appender(weekday.TAG);
            status = true;
        }
        else  if(anno instanceof Year){
            Year year = (Year) anno;
            appender.appender(year.TAG);
            status = true;
        }
        return status;
    }

    private static boolean parseParamVarchar(Appender appender, Annotation anno) {
        boolean status = false;
        if(anno instanceof CharLength){
            CharLength charLength = ((CharLength) anno);
            appender.appender(charLength.TAG);
            status = true;
        }
        else if(anno instanceof Concat){
            Concat concat = (Concat) anno;
            appender.appenderPosition(concat.TAG, concat.position(), concat.value());
            status = true;
        }
        else if(anno instanceof ConcatWs){
            ConcatWs concat = (ConcatWs) anno;
            String separator = concat.separator();
            String[] value = concat.value();
            LinkedList<Object> params = new LinkedList<>();
            for(String conc : value){
                //Allow empty
                params.add(conc);
            }
            //separator not null
            params.addFirst(separator);
            // concat.position() + 1 for separator always as first element
            appender.appenderPosition(concat.TAG, concat.position() + 1, params);
            status = true;
        }
        else if(anno instanceof GroupConcat){
            GroupConcat groupConcat = (GroupConcat) anno;
            boolean distinct = groupConcat.distinct();
            String distinctKeyWord = distinct ? " distinct " : " ";
            GroupConcat.OrderBy orderBy = groupConcat.orderBy();
            String orderByColName = orderBy.colName();
            GroupConcat.Sort sort = orderBy.sort();
            String groupConcatStatment = "";
            if(StringUtils.isNotBlank(orderByColName)){
                groupConcatStatment = " order by "+orderByColName +" "+ sort.name();
            }
            String separator = groupConcat.separator();
            if(StringUtils.isNotBlank(separator)){
                //SEPARATOR
                groupConcatStatment = groupConcatStatment + " separator '" +separator+"'";
            }
            appender.appenderParamNotSeparatorComma(groupConcat.TAG, distinctKeyWord, groupConcatStatment);
            status = true;
        }
        else if(anno instanceof Length){
            Length length = (Length) anno;
            appender.appender(length.TAG);
            status = true;
        }
        else if(anno instanceof Lower){
            Lower lower = (Lower) anno;
            appender.appender(lower.TAG);
            status = true;
        }
        else if(anno instanceof LTrim){
            LTrim lTrim = (LTrim) anno;
            appender.appender(lTrim.TAG);
            status = true;
        }
        else if(anno instanceof Repeat){
            Repeat repeat = (Repeat) anno;
            int value = repeat.value();
            appender.appender(repeat.TAG,value);
            status = true;
        }
        else if(anno instanceof Replace){
            Replace replace = (Replace) anno;
            String ep1 = replace.ep1();
            String ep2 = replace.ep2();
            appender.appender(replace.TAG, ep1, ep2);
            status = true;
        }
        else if(anno instanceof RTrim){
            RTrim rTrim = (RTrim)anno;
            appender.appender(rTrim.TAG);
            status = true;
        }
        else if(anno instanceof Strcmp){
            Strcmp strcmp = (Strcmp) anno;
            appender.appender(Strcmp.TAG,strcmp.value());
            status = true;
        }
        else if(anno instanceof SubString){
            SubString subString = (SubString) anno;
            String ep1 = subString.ep1();
            String ep2 = subString.ep2();
            appender.appender(subString.TAG, ep1, ep2);
            status = true;
        }
        else if(anno instanceof Trim){
            Trim trim = (Trim) anno;
            appender.appender(trim.TAG);
            status = true;
        }
        else if(anno instanceof Upper){
            Upper upper = (Upper) anno;
            appender.appender(upper.TAG);
            status = true;
        }
        return status;
    }

    private static boolean parseParamCommon(Appender appender, Annotation anno) {
        boolean status = false;
        if(anno instanceof Left){
            Left left = (Left) anno;
            int value = left.value();
            appender.appender(left.TAG, value);
            status = true;

        }else if(anno instanceof Right){
            Right right = (Right) anno;
            int value = right.value();
            appender.appender(right.TAG, value);
            status = true;
        }
        else if(anno instanceof IfNull){
            IfNull ifNull = (IfNull) anno;
            String value = ifNull.value();
            appender.appender(ifNull.TAG, value);
            status = true;
        }else if(anno instanceof If){
            If if_ = (If) anno;
            String expr1 = if_.ep1();
            String expr2 = if_.ep2();
            appender.appender(if_.TAG, expr1, expr2);
            status = true;
        }else if(anno instanceof IfEq){
            IfEq ifEq = (IfEq) anno;
            String ep1 = ifEq.ep1();
            String ep2 = ifEq.ep2();
            String eq = ifEq.eq();
            appender.appenderExpr(If.TAG, IfEq.EXPR + eq, ep1, ep2);
            status = true;
        } else if(anno instanceof IfGt){
            IfGt ifGt = (IfGt) anno;
            String ep1 = ifGt.ep1();
            String ep2 = ifGt.ep2();
            String gt = ifGt.gt();
            appender.appenderExpr(If.TAG, IfGt.EXPR + gt, ep1, ep2);
            status = true;
        } else if(anno instanceof IfGte){
            IfGte ifEq = (IfGte) anno;
            String ep1 = ifEq.ep1();
            String ep2 = ifEq.ep2();
            String gte = ifEq.gte();
            appender.appenderExpr(If.TAG, IfGte.EXPR + gte, ep1, ep2);
            status = true;
        }else if(anno instanceof IfLt){
            IfLt ifEq = (IfLt) anno;
            String ep1 = ifEq.ep1();
            String ep2 = ifEq.ep2();
            String lt = ifEq.lt();
            appender.appenderExpr(If.TAG, IfLt.EXPR + lt, ep1, ep2);
            status = true;
        }else if(anno instanceof IfLte){
            IfLte ifEq = (IfLte) anno;
            String ep1 = ifEq.ep1();
            String ep2 = ifEq.ep2();
            String lte = ifEq.lte();
            appender.appenderExpr(If.TAG, IfLte.EXPR + lte, ep1, ep2);
            status = true;
        }else if(anno instanceof IfNeq){
            IfNeq ifEq = (IfNeq) anno;
            String ep1 = ifEq.ep1();
            String ep2 = ifEq.ep2();
            String neq = ifEq.neq();
            appender.appenderExpr(If.TAG, IfNeq.EXPR + neq, ep1, ep2);
            status = true;
        }else if(anno instanceof IfNotNull){
            IfNotNull ifNotNull = (IfNotNull) anno;
            String value = ifNotNull.value();
            String ifNullValue = ifNotNull.ifNull();
            appender.appenderExpr(If.TAG, IfNotNull.EXPR ,value, ifNullValue );
            status = true;
        }
        return status;
    }

    static class Appender{
        private Class modelClass;
        private Field field;
        private String fieldName;
        private String colName;
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

        public Appender appenderParamNotSeparatorComma(String funName, String expr,  Object... param){
            StringBuilder appenderLeftOut = new StringBuilder();
            appenderLeftOut.append(funName);
            appenderLeftOut.append("(");
            appenderLeftOut.append(expr);
            appenderLeftOut.append(this.appenderLeft);
            if(param != null){
                List<String> collect = Arrays.stream(param).filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList());
                if(collect!=null &&collect.size() > 0){
                    String join = String.join(" ", collect);
                    appenderRight.append(" ");
                    appenderRight.append(join);
                }
            }
            appenderRight.append(")");
            this.appenderLeft = appenderLeftOut;
            return this;
        }

        public Appender appenderPosition(String funName, int position, Object... param){
            StringBuilder appenderLeftOut = new StringBuilder();
            appenderLeftOut.append(funName);
            appenderLeftOut.append("(");

            if(param != null){
                List<String> collect = Arrays.stream(param).filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList());
                if(position < 0){
                    position = collect.size() + position+1;
                }
                Assert.isTrue(position <= collect.size() && position >= 0 ,funName +" position out of bounds");
                List<String> left = collect.subList(0,position);
                List<String> right = collect.subList(position, collect.size() );
                if(left!=null &&left.size() > 0){
                    String join = String.join(",", left);

                    appenderLeftOut.append(join);
                    appenderLeftOut.append(",");
                }
                if(right!=null &&right.size() > 0){
                    String join = String.join(",", right);
                    appenderRight.append(",");
                    appenderRight.append(join);

                }

            }
            appenderLeftOut.append(this.appenderLeft);
            appenderRight.append(")");
            this.appenderLeft = appenderLeftOut;
            return this;
        }

        public Appender appenderPosition(String funName, int position, List<Object> param){
            StringBuilder appenderLeftOut = new StringBuilder();
            appenderLeftOut.append(funName);
            appenderLeftOut.append("(");

            if(param != null){
                List<String> collect = param.stream().filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList());
                if(position < 0){
                    position = collect.size() + position+1;
                }
                Assert.isTrue(position <= collect.size() && position >= 0 ,funName +" position out of bounds");
                List<String> left = collect.subList(0,position);
                List<String> right = collect.subList(position, collect.size() );
                if(left!=null &&left.size() > 0){
                    String join = String.join(",", left);

                    appenderLeftOut.append(join);
                    appenderLeftOut.append(",");
                }
                if(right!=null &&right.size() > 0){
                    String join = String.join(",", right);
                    appenderRight.append(",");
                    appenderRight.append(join);

                }

            }
            appenderLeftOut.append(this.appenderLeft);
            appenderRight.append(")");
            this.appenderLeft = appenderLeftOut;
            return this;
        }

        public Appender appenderExpr(String funName,String expr, Object... param){
            StringBuilder appenderLeftOut = new StringBuilder();
            appenderLeftOut.append(funName);
            appenderLeftOut.append("(");
            appenderLeftOut.append(this.appenderLeft);
            if(param != null){
                List<String> collect = Arrays.stream(param).filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList());
                if(collect!=null &&collect.size() > 0){
                    String join = String.join(",", collect);
                    appenderRight.append(expr);
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


        public Appender(Class modelClass, Field field, String  colName){
            this.modelClass = modelClass;
            this.field = field;
            this.fieldName = field.getName();
            this.colName = colName;
        }

        public boolean isNoneParam() {
            return isNoneParam;
        }

        public String getNoParamFunName(){
            return this.noParamFunName;
        }
    }
}
