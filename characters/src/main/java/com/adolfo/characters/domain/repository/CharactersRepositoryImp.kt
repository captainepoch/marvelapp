package com.adolfo.characters.domain.repository

import com.adolfo.characters.data.local.CharactersLocal
import com.adolfo.characters.data.models.data.toCharactersView
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.models.entity.toCharacters
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.data.service.CharactersApi
import com.adolfo.core.exception.Failure
import com.adolfo.core.exception.Failure.NetworkConnection
import com.adolfo.core.exception.Failure.ServerError
import com.adolfo.core.functional.Either
import com.adolfo.core.functional.State.Success
import com.adolfo.core.network.NetworkTools
import javax.net.ssl.SSLHandshakeException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharactersRepositoryImp(
    private val networkTools: NetworkTools,
    private val service: CharactersApi,
    private val local: CharactersLocal
) : CharactersRepository {

    override suspend fun getCharacters(
        offset: Int?,
        isPaginated: Boolean,
        limit: Int?
    ): Either<Failure, CharactersView>  {
        return when (val result = getCharactersFromSource(offset, isPaginated, limit)) {
            is Either.Left -> {
                result
            }

            is Either.Right -> {
                Either.Right(result.data.toCharacters().toCharactersView())
            }
        }
    }

    private suspend fun getCharactersFromSource(
        offset: Int?,
        isPaginated: Boolean,
        limit: Int?
    ): Either<Failure, CharactersEntity> {
        return runCatching {
            val localData = local.getAllCharacters()
            if (!localData.isNullOrEmpty() && !isPaginated) {
                Either.Right(CharactersEntity.fromList(localData))
            } else {
                getCharactersFromService(offset, limit)
            }
        }.map {
            it
        }.getOrElse { throwable ->
            when (throwable) {
                is SSLHandshakeException -> Either.Left(ServerError(ServerError.SSL_HANDSHAKE_EXCEPTION))
                else -> Either.Left(Failure.Throwable(throwable))
            }
        }
    }

    private suspend fun getCharactersFromService(
        offset: Int?,
        limit: Int?
    ): Either<Failure, CharactersEntity> {
        return if (networkTools.hasInternetConnection()) {
            service.getCharacters(offset, limit).run {
                if (isSuccessful && body() != null) {
                    val data = body()!!.data
                    local.saveCharacters(data.results.orEmpty())
                    Either.Right(data)
                } else {
                    Either.Left(ServerError(code()))
                }
            }
        } else {
            Either.Left(NetworkConnection)
        }
    }

    override suspend fun getCharacter(id: Int?) = flow {
        emit(Success(CharacterEntity.empty().toCharacter().toCharacterView()))
        /*when (val data: State<CharacterEntity> = datasource.getCharacter(id)) {
            is Success<CharacterEntity> -> emit(
                Success(
                    data.data.toCharacter().toCharacterView()
                )
            )

            is Error -> emit(Error(data.failure))
        }*/
    }
}
