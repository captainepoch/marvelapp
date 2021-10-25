package com.adolfo.core.functional

import java.util.concurrent.atomic.AtomicBoolean

class Event<out T>(private val content: T) {

    private val hasBeenHandled = AtomicBoolean(false)

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (!hasBeenHandled.getAndSet(true)) {
            content
        } else {
            null
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
