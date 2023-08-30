package com.javaoffers.brief.modelhelper.core.gkey;

import com.javaoffers.brief.modelhelper.core.KeyGenerate;
import com.javaoffers.brief.modelhelper.core.UniqueKeyGenerate;
import com.javaoffers.brief.modelhelper.exception.BaseException;
import com.javaoffers.brief.modelhelper.utils.Assert;
import com.javaoffers.brief.modelhelper.utils.NetUtils;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 雪花算法.
 *
 * @author mingJie
 */
public class SnowflakeKey implements UniqueKeyGenerate<Long> {
    WorkSeq workSeq;
    @Override
    public Long generate() {
        return workSeq.next();
    }

    public SnowflakeKey() {
        //work
        String innetIp = NetUtils.getInnetIp();
        byte[] bytes = innetIp.getBytes();
        int sum = 0;
        for(byte b : bytes){
            sum = b + sum;
        }
        sum = sum % WorkSeq.max;
        this.workSeq = new WorkSeq(sum);
    }
}

//10bit
class WorkSeq {
    static final int mv = 10;
    static final int max = ((1<<mv)-1);
    int seq;
    AtomicReference<TimeSeq> timeSeq;

    public WorkSeq(int seq) {
        this.seq = seq;
        Assert.isTrue(this.seq < max);
        this.timeSeq = new AtomicReference (new TimeSeq());
    }

    public long next(){
        for(;;){
            long nowMil = System.currentTimeMillis();
            TimeSeq timeSeq = this.timeSeq.get();
            //时间倒退. 刚好是当前时间
            if(nowMil <= timeSeq.seq){
                int next = timeSeq.atoSeq.next();
                if(next > 0){
                    long build = build(this.seq, timeSeq.seq, next);
                    return build;
                }
                //满了
                //Thread.yield();
            }else {
                //时间过期
                this.timeSeq.compareAndSet(timeSeq, new TimeSeq(nowMil));
            }
        }

    }

    public long build(long workSeq, long timeSeq, long atoSeq){
        long len = 63;
        long key = workSeq << (len = len- WorkSeq.mv);
        key = key | (timeSeq << (len = len - TimeSeq.mv));
        key = key | (atoSeq << (len - AtoSeq.mv));
        return key;
    }
}

//41 bit
class TimeSeq {
    static final long mv = 41;
    static final long max = ( (1L << mv) - 1L);
    long seq;
    AtoSeq atoSeq;
    public TimeSeq() {
        this.seq = System.currentTimeMillis();
        this.atoSeq = new AtoSeq();
        Assert.isTrue(this.seq < max);
    }

    public TimeSeq(long seq) {
        this.seq = seq;
        this.atoSeq = new AtoSeq();
        Assert.isTrue(this.seq < max);
    }
}

//12bit
class AtoSeq {
    static final int mv = 12;
    static final int max = ((1<<mv)-1);
    AtomicInteger seq;

    public AtoSeq() {
        this.seq = new AtomicInteger(-1);
    }

    public int next(){
        int get = this.seq.incrementAndGet();
        if(get > max){
            return -1;
        }
        return get;
    }
}

