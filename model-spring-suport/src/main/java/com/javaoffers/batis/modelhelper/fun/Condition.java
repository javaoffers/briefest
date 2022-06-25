package com.javaoffers.batis.modelhelper.fun;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/**
 * @Description: 条件借口
 * @Auther: create by cmj on 2022/5/2 02:22
 */
public interface Condition {

    public ConditionTag getConditionTag();

    public String getSql();

    public Map<String, Object> getParams();

    static ThreadLocal<AtomicLong> idxThreadLocal = new InheritableThreadLocal<AtomicLong>(){
        @Override
        protected AtomicLong initialValue() {
            return new AtomicLong(0);
        }
    };

    static ThreadLocal<Map<Condition, Function<String, String>>> wordThreadLoacl =
            new InheritableThreadLocal<Map<Condition, Function<String, String>>>() {
                @Override
                protected Map<Condition, Function<String, String>> initialValue() {
                    return new HashMap<>();
                }
            };

    default long getNextLong() {
        AtomicLong idx = idxThreadLocal.get();
        long idxAndIncrement = idx.getAndIncrement();
        if (idxAndIncrement > 99999) {
            for (; !idx.compareAndSet(idx.get(), 0); ) {
            }
        }
        return idxAndIncrement;
    }

    default String andOrWord(String word) {
        Function<String, String> function = wordThreadLoacl.get().get(this);
        if (function != null) {
            word = function.apply(word);
        }
        return word;
    }

    default void andOrWord(Function<String, String> word) {
        wordThreadLoacl.get().put(this, word);
    }

    default void clean() {
        wordThreadLoacl.remove();
    }

}
