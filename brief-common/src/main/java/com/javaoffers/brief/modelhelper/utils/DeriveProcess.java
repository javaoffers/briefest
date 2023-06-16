package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveFlag;
import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveInfo;
import com.javaoffers.brief.modelhelper.anno.derive.flag.IsDel;
import com.javaoffers.brief.modelhelper.anno.derive.flag.RowStatus;
import com.javaoffers.brief.modelhelper.anno.derive.flag.Version;
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
        DeriveInfo deriveInfo = new DeriveInfo(colName, colF);
        //version
        Version annotation = colF.getAnnotation(Version.class);
        if(annotation != null){
            tableInfo.putDeriveColName(DeriveFlag.VERSION, deriveInfo);
            Assert.isTrue(Number.class.isAssignableFrom(colF.getType()),"version col name " +
                    colF.getDeclaringClass() +"."+colF.getName()
                    +" must be Number");
        }
        //isDel
        if(IsDel.class.isAssignableFrom(colF.getType())){
            Assert.isTrue(tableInfo.getDeriveColName(DeriveFlag.IS_DEL) == null ,"Repeated tombstone fields, " +
                    "IsDel or RowStatus cannot be used multiple times at the same time, and cannot be reused");
            tableInfo.putDeriveColName(DeriveFlag.IS_DEL, deriveInfo);

        }

        //isDel
        if(RowStatus.class.isAssignableFrom(colF.getType())){
            Assert.isTrue(tableInfo.getDeriveColName(DeriveFlag.IS_DEL) == null ,"Repeated tombstone fields, " +
                    "IsDel or RowStatus cannot be used multiple times at the same time, and cannot be reused");
            tableInfo.putDeriveColName(DeriveFlag.IS_DEL, deriveInfo);
        }
    }
}
