package com.adolfo.core.functional

sealed class Either<out L, out R> {

    data class Left<out L, out R>(val value: L) : Either<L, R>()
    data class Right<out L, out R>(val value: R) : Either<L, R>()

    fun isRight() = this is Right

    fun isLeft() = this is Left

    fun asLeftOrNull() = (this as? Left<L, R>)?.value

    fun asRightOrNull() = (this as? Right<L, R>)?.value

    fun asLeft(): L = (this as Left<L, R>).value

    fun asRight(): R = (this as Right<L, R>).value

    inline fun <N> map(crossinline mapper: (R) -> N): Either<L, N> {
        return if (this.isLeft()) {
            Left(this.asLeft())
        } else {
            Right(mapper(this.asRight()))
        }
    }
}
