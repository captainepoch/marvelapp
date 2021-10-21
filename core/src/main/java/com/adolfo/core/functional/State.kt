package com.adolfo.core.functional

import com.adolfo.core.exception.Failure

sealed class State<out T> {

    data class Success<out T>(val data: T) : State<T>()

    data class Error(val failure: Failure) : State<Nothing>()
}
