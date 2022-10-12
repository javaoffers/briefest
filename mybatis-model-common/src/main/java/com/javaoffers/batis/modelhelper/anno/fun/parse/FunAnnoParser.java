package com.javaoffers.batis.modelhelper.anno.fun.parse;

import com.javaoffers.batis.modelhelper.anno.ColName;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.math.Rand;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.Curdate;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.CurrentDate;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.CurrentTime;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.Curtime;
import com.javaoffers.batis.modelhelper.anno.fun.noneparam.time.Now;
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
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * @author mingJie
 */
public class FunAnnoParser {

    public static final String DEFAULT_VALUE = "";


    public static String parse(Field field){
        Assert.isTrue(field != null,"the field is null");
        Annotation[] allAnno = field.getDeclaredAnnotations();
        if(allAnno == null || allAnno.length == 0){
            return DEFAULT_VALUE;
        }
        //先查询出@ColName注解
        LinkedHashMap<String, Annotation> annoMap = new LinkedHashMap<>();

        ColName colName = null;
        for(Annotation anno : allAnno){
            if(anno instanceof ColName){
                colName = (ColName) anno;
                continue;
            }
            //No reflection here is for performance
            if(anno instanceof Left){
                annoMap.put(((Left) anno).TAG,anno);
            }
            if(anno instanceof IfNull){
                annoMap.put(((IfNull) anno).TAG,anno);
            }
            if(anno instanceof CharLength){
                annoMap.put(((CharLength) anno).TAG,anno);
            }
            if(anno instanceof Concat){
                annoMap.put(((Concat) anno).TAG,anno);
            }
            if(anno instanceof Length){
                annoMap.put(((Length) anno).TAG,anno);
            }
            if(anno instanceof Lower){
                annoMap.put(((Lower) anno).TAG,anno);
            }
            if(anno instanceof Strcmp){
                annoMap.put(((Strcmp) anno).TAG,anno);
            }
            if(anno instanceof Upper){
                annoMap.put(((Upper) anno).TAG,anno);
            }
            if(anno instanceof Dayname){
                annoMap.put(((Dayname) anno).TAG,anno);
            }
            if(anno instanceof Hour){
                annoMap.put(((Hour) anno).TAG,anno);
            }
            if(anno instanceof Minute){
                annoMap.put(((Minute) anno).TAG,anno);
            }
            if(anno instanceof Month){
                annoMap.put(((Month) anno).TAG,anno);
            }
            if(anno instanceof Monthname){
                annoMap.put(((Monthname) anno).TAG,anno);
            }
            if(anno instanceof Week){
                annoMap.put(((Week) anno).TAG,anno);
            }
            if(anno instanceof Weekday){
                annoMap.put(((Weekday) anno).TAG,anno);
            }
            if(anno instanceof Year){
                annoMap.put(((Year) anno).TAG,anno);
            }
            if(anno instanceof Abs){
                annoMap.put(((Abs) anno).TAG,anno);
            }
            if(anno instanceof Ceil){
                annoMap.put(((Ceil) anno).TAG,anno);
            }
            if(anno instanceof Floor){
                annoMap.put(((Floor) anno).TAG,anno);
            }
            if(anno instanceof Mod){
                annoMap.put(((Mod) anno).TAG,anno);
            }
            if(anno instanceof Round){
                annoMap.put(((Round) anno).TAG,anno);
            }
            if(anno instanceof Truncate){
                annoMap.put(((Truncate) anno).TAG,anno);
            }
            if(anno instanceof Curdate){
                annoMap.put(((Curdate) anno).TAG,anno);
            }
            if(anno instanceof CurrentDate){
                annoMap.put(((CurrentDate) anno).TAG,anno);
            }
            if(anno instanceof CurrentTime){
                annoMap.put(((CurrentTime) anno).TAG,anno);
            }
            if(anno instanceof Curtime){
                annoMap.put(((Curtime) anno).TAG,anno);
            }
            if(anno instanceof Now){
                annoMap.put(((Now) anno).TAG,anno);
            }
            if(anno instanceof Rand){
                annoMap.put(((Rand) anno).TAG,anno);
            }

        }
        return null; //TODO
    }

    static class Appender{
        StringBuilder appenderLeft = new StringBuilder();
        StringBuilder appenderRight = new StringBuilder();
        String coreSql;
        public Appender appender(String funName,String... param){
            appenderLeft.append(funName);
            appenderLeft.append("(");
            if(param != null){
                String join = String.join(",", param);
                appenderRight.append(join);
            }
            appenderRight.append(")");
            return this;
        }
    }
}
