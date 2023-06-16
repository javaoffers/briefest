package com.javaoffers.brief.modelhelper.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 利用AQS 实现 FutureLock.当线程获取锁不成功时说明已经被其他线程所占用。
 * 那么我们可以可以调用get（） 或 get（timeOut）来获取结果.
 * 并且 Future 提供时间超时特性获取. tryLock 成功后不可再次tryLock。
 * 此锁不可以重入。必须要在reset 之后才可以重用。reset包括手动和超时自动
 * 设计的特点：主要分两种角色：
 *      1.持有 lock 角色：打开状态的线程，并且只有该线程才可以显示调用 unlock。
 *                 并且只有持有lock角色的线程在调用unlock之后，那些等待
 *                 future的线程才有权限去阅读数据。
 *      2.等待future角色：等待 future的线程。等待结果。（FIFO）
 * create by cmj on 2022-05-27
 */
public class FutureLock<T>{

    TimeOutLockSupport<T> timeOutLockSupport ;

    private long nanos = -1;//多少那秒过期可以reset. 过期后可自动重入（重新使用）,-1 永不过期

    private AtomicLong windowOffset;//窗户偏移量

    public FutureLock(TimeUnit timeUnit, long timeLong) {
        if(timeLong < 0){
            timeLong = -1;
        }
        this.nanos = timeUnit.toNanos(timeLong);
        this.windowOffset = new AtomicLong(System.nanoTime() / nanos * nanos);
        this.timeOutLockSupport = new TimeOutLockSupport<T>();
    }

    /**
     * 默认不具有超时过期reset.
     */
    public FutureLock() {
        this.timeOutLockSupport = new TimeOutLockSupport<T>();
    }

    static class TimeOutLockSupport<T> extends AbstractQueuedSynchronizer {

        //完成的结果
        private volatile T future;

        //reset之前的完成结果。reset时会将 future 的值赋予 resetFuture
        private volatile T resetFuture;

        //是否已经完成
        private volatile boolean isDone = false;

        // 将锁打开
        private static  final  int openState = 1;

        //将锁关闭
        private static  final  int closeState = 0;

        //将锁关闭
        private static  final  int asynState = 0;

        //用于排队的线程等待 获取 future 的线程
        private static  final  int futureState = -1;

        public TimeOutLockSupport() { }

        /**
         * 获取锁，并返回获取状态， 不允许重入
         * @return， true 为成功， false 为失败。并且不会进入等待队列
         */
        public boolean tryLock() {
            if(this.isDone) return false;
            boolean lockState = false;
            //获取成功后锁状态永远保持为 1 ，同一线程不可重入。
            if(compareAndSetState(closeState, openState)){
                acquire(openState);
                lockState = true;
                // isDone 如果为true表示已经使用过，在没有reset的情况下不运行再次使用
                // isDone 如果为false表示被reset或刚被初始化。
                if(isDone){
                    release(closeState);// 唤醒等待的线程
                    lockState = false;
                }
            }
            return lockState;
        }

        /**
         * 解锁，只有持有lock线程才有资格解锁.会立刻释放.
         * @param t
         */
        public void unlock(T t){
            //如果排他线程和当前线程一致，则释放。并且肯定要释放成功
            if(getExclusiveOwnerThread() == Thread.currentThread() && !isDone){
                this.future = t;
                this.resetFuture = t;
                this.isDone = true;
                release(closeState);
            }else {
                //未持有锁的线程进行释放 或 重复释放
                throw new IllegalMonitorStateException();
            }
        }

        /**
         * 专门为异步线程释放锁。
         * 线程A 获取锁，但是A 中存在某线程B。A 需要 B完成后并返回结果再继续。
         * 此时A 可以调用get等待B的响应。B 需要通过此方法进行释放完成任务
         * @param t
         */
        public synchronized void unlockAsyn(T t){
            //尝试释放。
            if(!isDone){
                this.future = t;
                this.resetFuture = t;
                this.isDone = true;
                release(asynState);
            }
        }

        /**
         * tryAcquire: 针对两种情况:
         *             1：tryLock成功 (已经将closeState 转变为 openState)
         *             2：get() 操作 (尝试将closeState 转变为futureState)
         * @param arg 只能为 1（openState） 或 -1（futureState）
         * @return
         */
        protected boolean tryAcquire(int arg) {
            boolean state = false;
            //只有在 tryLock -> compareAndSetState(closeState, openState)成功并且isDone为false才有机会
            if(arg == openState){
                Thread thread = Thread.currentThread();
                setExclusiveOwnerThread(thread);
                state = true;
            }
            // 针对与get()操作 具有 FIFO 特性.
            else if( !hasQueuedPredecessors()){ //如果当前线程之前没有等待的线程则尝试cas
                // cas 失败则进入等待队列。
                state = compareAndSetState(closeState, futureState);
            }
            return state;
        }

        /**
         * tryRelease 只用作两种情况，
         * 1:  tryLock() acquire成功，并对其进行 unlock (closeState && currentThread == exclusiveOwnerThread),
         *                           unlockAsyn (asynState).
         * 2:  get()/reset() acquire成功 并进行释放唤醒下一个get()/reset() (要将futureState 转变为 closeState)
         * @param arg 释放标识
         * @return
         */
        protected boolean tryRelease(int arg) {
            // 只有在 tryLock -> compareAndSetState(closeState, openState) 成功才有机会
            if( (arg == closeState && Thread.currentThread() == getExclusiveOwnerThread())
                    || arg == asynState){
                //将开状态改为关状态
                return compareAndSetState(openState, closeState);
            }
            //等待获取future 的线程不用设置排他，并在只有在获取 acquire 成功的情况下 可以直接释放。
            return compareAndSetState( futureState, closeState);
        }

        /**
         * get()有两种情况：
         * 1：如果isDone为true则直接返回结果，
         * 2：false则会进入 closeState -> futureState 可能会进入等待直至成功。（成功由unlock，unlockAsyn或前一个get()影响）
         *                futureState -> closeState 释放并唤醒下一个等待的线程
         * 注意：应小心在tryLock内使用get(通常不建议这么做)。除非是在异步unlockAsyn场景下或unlock之后。否则会永久等待。
         * @return result
         */
        public T get(){
            // 避免不必要的竞争
            if( !this.isDone ){
                //尝试获取锁，如果失败则进入等待队列
                acquire(futureState);
                //释放锁，移除队列
                release(futureState);
            }
            //返回结果
            return future;
        }

        public T getOrOld(){
            // 避免不必要的竞争
            if( !this.isDone ){
                //尝试获取锁，如果失败则进入等待队列
                acquire(futureState);
                //释放锁，移除队列
                release(futureState);
            }
            //返回结果。在reset之前，resetFuture和future状态一致
            return resetFuture;
        }

        T get(long timeout, TimeUnit unit) throws InterruptedException {
            if(!this.isDone){
                if(tryAcquireNanos(futureState,unit.toNanos(timeout))){
                    release(futureState);
                }
            }
            return future;
        }

        T getOrOld(long timeout, TimeUnit unit) throws InterruptedException {
            if(!this.isDone){
                if(tryAcquireNanos(futureState,unit.toNanos(timeout))){
                    release(futureState);
                }
            }
            //返回结果。在reset之前，resetFuture和future状态一致
            return resetFuture;
        }

        boolean isDone(){
            return this.isDone;
        }

        /**(l: lock, u: unlock, r: reset , f: get()).
         * l - done:true - u -- r -- r - f - f
         * 在 u - r 之间的 tryLock 是失败的。
         * 在 u 执行完后的第一个 r称为 r1, 在r1之前u之后是不会出现 l 的。
         * r1 r .. : r 无意义
         * r1 f .. ：f 获取为null
         * r1 l .. : 新一轮 lock 开始
         * 因此：
         * u - r1 之间如果存在，且只会存在 f. 在 r1 .... l 之间的 r 为无意义，f 获取为null。
         * l 是非公平的。因此 l - r1 之间的任何 f 都将归于此 l。
         */
        public void reset() {
            if(this.isDone){
                acquire(futureState);
                setExclusiveOwnerThread(null);
                this.isDone = false;
                this.future = null;
                release(futureState);
            }
        }

    }

    /**
     * 尝试获取锁，不可重入
     * @return
     * @throws InterruptedException
     */
    public boolean tryLock() {
        boolean status = false;
        if(nanos == -1){
            //普通竞争lock
            //如果已经完成并没有reset，则会返回false.
            status = timeOutLockSupport.tryLock();
        }else{
            long cwo = System.nanoTime() / nanos * nanos;
            long oldWO = windowOffset.get();
            //检查是否过期重置
            if(cwo > oldWO && windowOffset.compareAndSet(oldWO,cwo)){
                if(isDone()){ // true: unlock, unlockAysn 成功
                    reset();
                }
            }
            //普通竞争lock
            //如果已经完成并没有reset，则会返回false.
            //已经过 ‘检查是否过期重置’步骤
            status = timeOutLockSupport.tryLock();
        }
        return status;
    }

    /**
     * 释放锁
     * @param t
     */
    public void unlock(T t){
        timeOutLockSupport.unlock(t);
    }

    /**
     * 异步场景进行释放
     * @param t
     */
    public void unlockAsyn(T t){
        timeOutLockSupport.unlockAsyn(t);
    }

    /**
     * 获取结果
     * @return
     */
    public T get(){
        return timeOutLockSupport.get();
    }
    /**
     * 获取结果
     * @return
     */
    public T getOrOld(){
        return timeOutLockSupport.getOrOld();
    }

    /**
     * 获取结果, 带有超时特性
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public T get(long timeout, TimeUnit unit) throws InterruptedException
    {
        return timeOutLockSupport.get(timeout, unit);
    }

    /**
     * 获取结果, 带有超时特性
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public T getOrOld(long timeout, TimeUnit unit) throws InterruptedException
    {
        return timeOutLockSupport.getOrOld(timeout, unit);
    }


    /**
     * 费否已经完成
     * @return
     */
    public boolean isDone(){
        return timeOutLockSupport.isDone();
    }

    /**
     * 重置后，可以再次使用futureLock。reset 只有在 unlock之后调用才生效
     * 注意：在reset之后并且在tryLock之前，进入futureState的线程
     * 则可能会返回null。reset 和 get 相同都会公平排队等待。所以
     * reset并不会立刻重置。当获取futureState 状态时才会进行释放。
     * 为了提高get成功率，建议在下一个时间窗口进行重置上一个窗口的
     * futureLock. 通常来讲越滞后get成功率越高。 避免返回null,建议
     * 使用getOrOld. 返回最新的或reset之前的数据。
     */
    public void reset(){
        timeOutLockSupport.reset();
    }
}


