package com.adolfo.characters.domain.repository

import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.service.CharactersService
import com.adolfo.core.exception.Failure.NetworkConnection
import com.adolfo.core.exception.Failure.ServerError
import com.adolfo.core.exception.Failure.Throwable
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Error
import com.adolfo.core.functional.State.Success
import com.adolfo.core.network.NetworkTools
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CharactersRepositoryImp(
    private val networkTools: NetworkTools,
    private val service: CharactersService
) : CharactersRepository {

    override fun getCharacters() = flow {
        emit(getCharactersFromService())
    }.catch { emit(Error(Throwable(it))) }

    private suspend fun getCharactersFromService(): State<CharactersEntity> {
        return if (networkTools.hasInternetConnection()) {
            service.getCharacters(10).run {
                if (isSuccessful && body() != null) {
                    Success(body()!!.data)
                } else {
                    Error(ServerError(code()))
                }
            }
        } else {
            Error(NetworkConnection)
        }
    }

    override fun getCharacter(id: Int) {
    }
}
