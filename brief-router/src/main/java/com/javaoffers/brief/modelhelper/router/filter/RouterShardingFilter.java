//package com.javaoffers.brief.modelhelper.router.filter;
//
//import com.javaoffers.brief.modelhelper.anno.derive.flag.DeriveInfo;
//import com.javaoffers.brief.modelhelper.filter.JqlExecutorChain;
//import com.javaoffers.brief.modelhelper.filter.JqlExecutorFilter;
//import com.javaoffers.brief.modelhelper.filter.JqlMetaInfo;
//import com.javaoffers.brief.modelhelper.router.ShardingDeriveFlag;
//import com.javaoffers.brief.modelhelper.router.anno.ShardingStrategy;
//import com.javaoffers.brief.modelhelper.router.strategy.ShardingTableColumInfo;
//import com.javaoffers.brief.modelhelper.router.strategy.ShardingTableStrategy;
//import com.javaoffers.brief.modelhelper.utils.TableInfo;
//
//import java.lang.reflect.Field;
//import java.util.concurrent.ThreadLocalRandom;
//
//public class RouterShardingFilter implements JqlExecutorFilter  {
//    public static ThreadLocal<ShardingTableColumInfo> shardingTable = new ThreadLocal();
//    @Override
//    public Object filter(JqlExecutorChain jqlExecutorChain) {
//        try {
//            JqlMetaInfo jqlMetaInfo = jqlExecutorChain.getJqlMetaInfo();
//            TableInfo tableInfo = jqlMetaInfo.getTableInfo();
//            //判断是否需要做分表解析
//            ShardingTableColumInfo deriveColName = (ShardingTableColumInfo)tableInfo.getDeriveColName(ShardingDeriveFlag.SHARDING_TABLE);
//            if(deriveColName != null){
//                shardingTable.set(deriveColName);
//            }
//            //做分表查询
//            return jqlExecutorChain.doChain();
//        }finally {
//            shardingTable.remove();
//        }
//
//    }
//}
