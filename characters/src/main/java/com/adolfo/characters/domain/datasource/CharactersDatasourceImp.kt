package com.adolfo.characters.domain.datasource

import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.data.service.CharactersService
import com.adolfo.characters.domain.local.CharactersLocal
import com.adolfo.core.exception.Failure
import com.adolfo.core.exception.Failure.NetworkConnection
import com.adolfo.core.exception.Failure.ServerError
import com.adolfo.core.extensions.isNotNullOrEmpty
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Error
import com.adolfo.core.functional.State.Success
import com.adolfo.core.network.NetworkTools
import javax.net.ssl.SSLHandshakeException

class CharactersDatasourceImp(
    private val networkTools: NetworkTools,
    private val service: CharactersService,
    private val local: CharactersLocal
) : CharactersDatasource {

    override suspend fun getCharacters(
        offset: Int?,
        isPaginated: Boolean,
        limit: Int?
    ): State<CharactersView> {
        return runCatching {
            val localData = local.getAllCharacters()
            if (localData.isNotNullOrEmpty() && !isPaginated) {
                Success(
                    CharactersEntity.fromList(localData.orEmpty())
                        .toCharacters().toCharactersView()
                )
            } else {
                getCharactersFromService(offset, limit)
            }
        }.map {
            it
        }.getOrElse { throwable ->
            when (throwable) {
                is SSLHandshakeException -> Error(ServerError(ServerError.SSL_HANDSHAKE_EXCEPTION))
                else -> Error(Failure.Throwable(throwable))
            }
        }
    }

    override suspend fun getCharacter(id: Int?): State<CharacterView> {
        return runCatching {
            getCharacterDetailFromService(id)
        }.map {
            it
        }.getOrElse { throwable ->
            Error(Failure.Throwable(throwable))
        }
    }

    private suspend fun getCharactersFromService(offset: Int?, limit: Int?): State<CharactersView> {
        return if (networkTools.hasInternetConnection()) {
            service.getCharacters(offset, limit).run {
                if (isSuccessful && body() != null) {
                    val data = body()!!.data
                    saveCharactersToLocal(data.results.orEmpty())
                    Success(data.toCharacters().toCharactersView())
                } else {
                    Error(ServerError(code()))
                }
            }
        } else {
            Error(NetworkConnection)
        }
    }

    private fun saveCharactersToLocal(list: List<CharacterEntity>) {
        local.saveCharacters(list)
    }

    private suspend fun getCharacterDetailFromService(id: Int?): State<CharacterView> {
        return if (networkTools.hasInternetConnection()) {
            service.getCharacter(id).run {
                if (isSuccessful && body() != null) {
                    val singleItem = body()!!.data.results?.first() ?: CharacterEntity.empty()
                    Success(singleItem.toCharacter().toCharacterView())
                } else {
                    Error(ServerError(code()))
                }
            }
        } else {
            Error(NetworkConnection)
        }
    }
}
