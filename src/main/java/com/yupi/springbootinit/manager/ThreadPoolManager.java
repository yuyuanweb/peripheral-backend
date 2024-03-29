package com.yupi.springbootinit.manager;

import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理
 *
 * @author pine
 */
@Service
public class ThreadPoolManager {

    private static final ExecutorService DEMO_THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            4,
            8,
            100,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000)
    );

    /**
     * 新建写书应用线程池
     *
     * @return {@link ExecutorService}
     */
    public ExecutorService getWriteBookThreadPool() {
        return DEMO_THREAD_POOL_EXECUTOR;
    }
}
