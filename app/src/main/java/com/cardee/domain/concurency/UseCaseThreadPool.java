package com.cardee.domain.concurency;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class UseCaseThreadPool {

    private static final int POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 4;
    private static final int TIMEOUT = 30;

    private final ThreadPoolExecutor mExecutor;

    /**
     * Check ArrayBlockingQueue capacity
     */
    public UseCaseThreadPool() {
        mExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public void execute(Runnable task) {
        mExecutor.execute(task);
    }
}
