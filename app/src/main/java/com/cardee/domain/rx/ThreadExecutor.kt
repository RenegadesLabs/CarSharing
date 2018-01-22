package com.cardee.domain.rx

import java.util.concurrent.*

class ThreadExecutor private constructor() : Executor {

    var checkCounter: Int = 0

    private val workQueue: BlockingQueue<Runnable>
    private val executor: ThreadPoolExecutor
    private val factory: TaskThreadFactory

    init {
        workQueue = LinkedBlockingQueue<Runnable>()
        factory = TaskThreadFactory()
        executor = ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT, workQueue, factory)
    }

    companion object {
        val INITIAL_POOL_SIZE = 3
        val MAX_POOL_SIZE = 5
        val KEEP_ALIVE_TIME = 20L
        val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
        @Volatile private var singleInstance: ThreadExecutor? = null

        fun getInstance(): ThreadExecutor? {
            if (singleInstance == null) {
                synchronized(this) {
                    if (singleInstance == null) {
                        singleInstance = ThreadExecutor()
                    }
                }
            }
            return singleInstance
        }
    }

    private class TaskThreadFactory : ThreadFactory {

        private var counter = 0

        override fun newThread(task: Runnable): Thread {
            counter++
            return Thread(task, THREAD_NAME + counter)
        }

        companion object {
            private val THREAD_NAME = "cardee_async_task_"
        }
    }

    override fun execute(task: Runnable) {
        executor.execute(task)
    }
}
