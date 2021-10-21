package com.adolfo.core.interactor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class UseCase<in P, out R> {

    abstract fun execute(params: P? = null): Flow<R>

    operator fun invoke(
        params: P,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<R> {
        return execute(params).flowOn(dispatcher)
    }

    class None
}
