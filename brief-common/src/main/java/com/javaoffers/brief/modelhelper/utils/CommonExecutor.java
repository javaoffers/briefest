package com.javaoffers.brief.modelhelper.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author mingJie
 */
public class CommonExecutor {
    public static final ScheduledExecutorService oneFixedScheduledThreadPool = Executors.newSingleThreadScheduledExecutor();
}
