package com.javaoffers.batis.modelhelper.consistant;

import java.sql.JDBCType;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * consistant vars
 */
public class ModelConsistants {
    public static final Class[] basePrimitiveClass = new Class[]{
            byte.class,
            short.class,
            int.class,
            long.class,
            float.class,
            double.class
    };
    public static final Class[] baseNumberClass = new Class[]{
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class
    };

    public static final Class[] UTIL_DATE= new Class[]{
            Date.class,
            java.sql.Date.class,
            Time.class,
            Timestamp.class,
    };

    public static final Map<Class,Class> numberPrimitivesMapping = new HashMap<Class,Class>();
    static {
        for(int i = 0; i < basePrimitiveClass.length; i++){
            numberPrimitivesMapping.put(baseNumberClass[i], basePrimitiveClass[i]);
            numberPrimitivesMapping.put(basePrimitiveClass[i], baseNumberClass[i]);
        }
    }

}
