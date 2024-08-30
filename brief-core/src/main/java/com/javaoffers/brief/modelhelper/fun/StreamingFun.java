package com.javaoffers.brief.modelhelper.fun;

import java.util.function.Consumer;

/**
 * Streaming queries. Avoid OOM.
 * TIP: Try not to perform join queries. If you use join queries, please remember to sort by the main table id.
 * Otherwise, data aggregation problems will occur
 * @param <M>
 */
public interface StreamingFun <M>{

    void stream(Consumer<M> consumer);
}
