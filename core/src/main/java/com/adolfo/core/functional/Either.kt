package com.adolfo.core.functional

sealed class Either<out L, out R> {

    data class Left<L>(val data: L) : Either<L, Nothing>()
    data class Right<R>(val data: R) : Either<Nothing, R>()

    fun isRight() = this is Right<R>

    fun isLeft() = this is Left<L>

    fun asLeftOrNull() = (this as? Left<L>)?.data

    fun asRightOrNull() = (this as? Right<R>)?.data

    fun asLeft(): L = (this as Left<L>).data

    fun asRight(): R = (this as Right<R>).data

    fun <T> fold(funLeft: (L) -> T, funRight: (R) -> T): T = when (this) {
        is Left -> funLeft(asLeft())
        is Right -> funRight(asRight())
    }

    suspend fun <T> coFold(funLeft: suspend (L) -> T, funRight: suspend (R) -> T): T {
        return when (this) {
            is Left -> funLeft(asLeft())
            is Right -> funRight(asRight())
        }
    }

    inline fun <N> map(crossinline mapper: (R) -> N): Either<L, N> {
        return if (this.isLeft()) {
            Left(this.asLeft())
        } else {
            Right(mapper(this.asRight()))
        }
    }
}
