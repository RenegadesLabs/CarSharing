package com.cardee.domain.rx

import java.util.concurrent.Executor

class ThreadExecutor private constructor() : Executor {

    var checkCounter: Int = 0

    companion object {
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


    override fun execute(task: Runnable?) {

    }
}
