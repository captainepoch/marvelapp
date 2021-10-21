package com.adolfo.core.exception

sealed class Failure {

    data class ServerError(val code: Int) : Failure()

    data class Throwable(val throwable: kotlin.Throwable?) : Failure()

    data class CustomError(val code: Int, val msg: String?) : Failure()

    object NetworkConnection : Failure()
}
