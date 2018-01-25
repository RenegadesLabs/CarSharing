package com.cardee.util

import com.cardee.data_source.OffersRepository

open class SingletonHolder<out I, in A> constructor(creator: (A) -> I) {
    private var creator: ((A) -> I)? = creator
    @Volatile private var instance: I? = null

    fun getInstance(arg: A): I {
        instance?.let { return it }
        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}
