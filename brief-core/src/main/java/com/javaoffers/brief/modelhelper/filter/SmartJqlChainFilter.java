package com.javaoffers.brief.modelhelper.filter;

import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveFlag;
import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveInfo;
import com.javaoffers.brief.modelhelper.log.JqlLogger;
import com.javaoffers.brief.modelhelper.utils.Assert;
import com.javaoffers.brief.modelhelper.utils.CglibProxyUtils;
import com.javaoffers.brief.modelhelper.utils.ColNameAndColValueUtils;
import com.javaoffers.brief.modelhelper.utils.ColumnInfo;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import com.javaoffers.brief.modelhelper.utils.TableInfo;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: SmartJqlChainFilter
 * <p>
 * Record the slow SQL, Secure Dynamic Updates when call setter methods of model.
 * </p>
 * @author: create by cmj on 2023/6/1 20:31
 */
public class SmartJqlChainFilter implements JqlChainFilter {
    @Override
    public Object filter(Chain<Object, SqlMetaInfo> chain) {
        long cost = 0;
        SqlMetaInfo metaInfo = chain.getMetaInfo();
        Class modelClass = metaInfo.getModelClass();
        long startTime = System.currentTimeMillis();
        Object o = chain.doChain();
        long endTime = System.currentTimeMillis();
        if ((cost = endTime - startTime) > JqlLogger.time) {
            if(o instanceof List){
                JqlLogger.infoSqlCost("COST TIME : {}, SIZE: {}", cost, ((List) o).size());
            }else{
                JqlLogger.infoSqlCost("COST TIME : {}", cost);
            }
        }
        return o;
    }
}
