package com.javaoffers.brief.modelhelper.core;

import com.javaoffers.brief.modelhelper.interceptor.JqlInterceptor;
import com.javaoffers.brief.modelhelper.utils.EnumValueUtils;
import com.javaoffers.brief.modelhelper.utils.InterceptorLoader;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 解析sql
 * @Auther: create by cmj on 2022/05/22 02:45
 */
public class SQLParse{
    private String sql;
    private String param;
    public final static String p = "(\\#\\{[0-9a-zA-Z-_]+\\})";
    public final static Pattern compile = Pattern.compile(p);
    private static List<JqlInterceptor> jqlInterceptorList;

    static {
        jqlInterceptorList = InterceptorLoader.loadJqlInterceptor();
    }

    private static String parseSql(String sql) {
        String s = sql.replaceAll(p, "?");
        return s;
    }

    public static SQL parseSqlParams(String sql, List<Map<String, Object>> paramMap) {
        ArrayList<Object[]> objects = new ArrayList<>(paramMap.size());
        ArrayList<String> keys = new ArrayList<>();
        Matcher matcher = compile.matcher(sql);
        while (matcher.find()) {
            String result = matcher.group(1);
            String paramKey = result.substring(2, result.length() - 1);
            keys.add(paramKey);
        }
        sql = parseSql(sql);
        SQL SQL = new SQL(sql, objects);
        SQL.setParamMap(paramMap);
        if(CollectionUtils.isNotEmpty(jqlInterceptorList)){
            for(JqlInterceptor jqlInterceptor : jqlInterceptorList){
                jqlInterceptor.handler(SQL);
            }
        }
        for (int i = 0, j = paramMap.size() - 1; paramMap != null && i <= j; i++, j--) {
            if (i == j) {
                Map<String, Object> pm = paramMap.get(i);
                Object[] params = new Object[keys.size()];
                for (int k = 0; k < keys.size(); k++) {
                    String paramKey = keys.get(k);
                    Object o = pm.get(paramKey);
                    if (o instanceof Id) {
                        o = ((Id) o).value();
                    } else if (o instanceof Enum) {
                        o = EnumValueUtils.getEnumValue(((Enum) o));
                    }
                    params[k] = o;
                }
                objects.add(params);
            } else {
                Map<String, Object> pmLeft = paramMap.get(i);
                Map<String, Object> pmRight = paramMap.get(j);
                Object[] paramsLeft = new Object[keys.size()];
                Object[] paramsRight = new Object[keys.size()];
                for (int k = 0; k < keys.size(); k++) {
                    String paramKey = keys.get(k);

                    Object oLeft = pmLeft.get(paramKey);
                    if (oLeft instanceof Id) {
                        oLeft = ((Id) oLeft).value();
                    } else if (oLeft instanceof Enum) {
                        oLeft = EnumValueUtils.getEnumValue(((Enum) oLeft));
                    }
                    paramsLeft[k] = oLeft;

                    Object oRight = pmRight.get(paramKey);
                    if (oRight instanceof Id) {
                        oRight = ((Id) oRight).value();
                    } else if (oRight instanceof Enum) {
                        oRight = EnumValueUtils.getEnumValue(((Enum) oRight));
                    }
                    paramsRight[k] = oRight;

                }
                objects.add(paramsLeft);
                objects.add(paramsRight);
            }
        }


        return SQL;

    }


    public static SQL getSQL(String sql, Map<String, Object> map) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        return SQLParse.parseSqlParams(sql, maps);
    }

}
