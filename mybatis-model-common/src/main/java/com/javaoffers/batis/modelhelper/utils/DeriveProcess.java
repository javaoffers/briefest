package com.javaoffers.batis.modelhelper.utils;

import com.javaoffers.batis.modelhelper.anno.derive.flag.DeriveFlag;
import com.javaoffers.batis.modelhelper.anno.derive.flag.DeriveInfo;
import com.javaoffers.batis.modelhelper.anno.derive.flag.Version;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;

/**
 * @description: Derivative processing.
 * @author: create by cmj on 2023/6/11 17:54
 */
public class DeriveProcess {

    public static void processDerive(TableInfo tableInfo, Field colF,String colName){
        Assert.isTrue(tableInfo != null, "tableInfo is null");
        Assert.isTrue(colF != null, "colF is null");
        Assert.isTrue(StringUtils.isNotBlank(colName), "colName is null");
        Version annotation = colF.getAnnotation(Version.class);
        if(annotation != null){
            DeriveInfo deriveInfo = new DeriveInfo(colName, colF);
            tableInfo.putDeriveColName(DeriveFlag.VERSION, deriveInfo);
            Assert.isTrue(Number.class.isAssignableFrom(colF.getType()),"version col name " +
                    colF.getDeclaringClass() +"."+colF.getName()
                    +" must be Number");
        }
    }
}
