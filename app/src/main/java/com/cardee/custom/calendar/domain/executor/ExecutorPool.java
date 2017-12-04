package com.cardee.custom.calendar.domain.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorPool {

    /**
     * timeout in seconds
     */
    public static final int ALIVE_TIMEOUT = 20;
    public static final int POOL_SIZE = 2;
    public static final int MAX_POOL_SIZE = 3;

    private ThreadPoolExecutor executor;

    ExecutorPool() {
        executor = new ThreadPoolExecutor(
                POOL_SIZE,
                MAX_POOL_SIZE,
                ALIVE_TIMEOUT,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    void put(Runnable command) {
        executor.execute(command);
    }
}
