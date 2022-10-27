package com.javaoffers.batis.modelhelper.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 解析sql
 * @Auther: create by cmj on 2022/05/22 02:45
 */
public class SQLParse {
    private String sql;
    private String param;
    public final static String p = "(\\#\\{[0-9a-zA-Z-_]+\\})";
    public final static Pattern compile = Pattern.compile(p);

    private String getParse() {
        return p;
    }

    private static String parseSql(String sql) {
        String s = sql.replaceAll(p, "?");
        return s;
    }

    /**
     * 结束解析
     *
     * @return 返回解析后的url
     */
    public String endParse() {
        return this.sql;
    }

    public static SQL parseSqlParams2(String sql, List<Map<String, Object>> paramMap) {

        ArrayList<Object[]> objects = new ArrayList<>(paramMap.size());
        for (int i = 0; paramMap != null && i < paramMap.size(); i++) {
            Map<String, Object> pm = paramMap.get(i);
            ArrayList<Object> params = new ArrayList<>();
            Matcher matcher = compile.matcher(sql);
            while (matcher.find()) {
                String result = matcher.group(1);
                String paramKey = result.substring(2, result.length() - 1);
                Object o = pm.get(paramKey);
                if (o instanceof Id) {
                    o = ((Id) o).value();
                }
                params.add(o);
            }
            objects.add(params.toArray());
        }
        sql = parseSql(sql);
        SQL SQL = new SQL(sql, objects);
        return SQL;

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

        for (int i = 0, j = paramMap.size()-1; paramMap != null && i <= j; i++,j--) {
            if(i==j){
                Map<String, Object> pm = paramMap.get(i);
                Object[] params = new Object[keys.size()];
                for(int k = 0; k < keys.size(); k++ ){
                    String paramKey = keys.get(k);
                    Object o = pm.get(paramKey);
                    if (o instanceof Id) {
                        o = ((Id) o).value();
                    }
                    params[k] = o;
                }
                objects.add(params);
            }else{
                Map<String, Object> pmLeft = paramMap.get(i);
                Map<String, Object> pmRight = paramMap.get(j);
                Object[] paramsLeft = new Object[keys.size()];
                Object[] paramsRight = new Object[keys.size()];
                for(int k = 0; k < keys.size(); k++ ){
                    String paramKey = keys.get(k);

                    Object oLeft = pmLeft.get(paramKey);
                    if (oLeft instanceof Id) {
                        oLeft = ((Id) oLeft).value();
                    }
                    paramsLeft[k] = oLeft;

                    Object oRight = pmRight.get(paramKey);
                    if (oRight instanceof Id) {
                        oRight = ((Id) oRight).value();
                    }
                    paramsRight[k] = oRight;

                }
                objects.add(paramsLeft);
                objects.add(paramsRight);
            }
        }
        sql = parseSql(sql);
        SQL SQL = new SQL(sql, objects);
        return SQL;

    }

    public static SQL getSQL(String sql, Map<String, Object> map) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        maps.add(map);
        return SQLParse.parseSqlParams(sql, maps);
    }

}
