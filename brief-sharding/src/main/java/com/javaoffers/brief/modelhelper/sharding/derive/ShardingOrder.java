package com.javaoffers.brief.modelhelper.sharding.derive;

import com.javaoffers.brief.modelhelper.fun.GetterFun;

import java.util.List;

/**
 * create by cmj
 */
public class ShardingOrder {

    private boolean asc;

    private GetterFun getterFun;

    public ShardingOrder(boolean asc, GetterFun getterFun) {
        this.asc = asc;
        this.getterFun = getterFun;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public GetterFun getGetterFun() {
        return getterFun;
    }

    public void setGetterFun(GetterFun getterFun) {
        this.getterFun = getterFun;
    }

    public int compareTo(Object o, Object o2) {
        Object reply = getterFun.reply(o);
        Object reply2 = getterFun.reply(o2);
        if (reply == null && reply2 == null) {
            return -1;
        } else if (reply != null && reply2 == null) {
            return 1;
        }else if (reply == null) {
            return -1;
        }else{
            if (reply2 instanceof Comparable && reply instanceof Comparable) {
                Comparable c1 = (Comparable) reply;
                Comparable c2 = (Comparable) reply2;
                return c1.compareTo(c2) * getAsc();
            }
            return reply.toString().compareTo(reply2.toString()) * getAsc();
        }
    }

    public int getAsc(){
        return asc ? 1 : -1;
    }
}