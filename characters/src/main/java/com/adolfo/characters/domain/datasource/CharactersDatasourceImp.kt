package com.adolfo.characters.domain.datasource

import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.service.CharactersService
import com.adolfo.characters.domain.local.CharactersLocal
import com.adolfo.core.exception.Failure.NetworkConnection
import com.adolfo.core.exception.Failure.ServerError
import com.adolfo.core.extensions.isNotNullOrEmpty
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Error
import com.adolfo.core.functional.State.Success
import com.adolfo.core.network.NetworkTools
import timber.log.Timber

class CharactersDatasourceImp(
    private val networkTools: NetworkTools,
    private val service: CharactersService,
    private val local: CharactersLocal
) : CharactersDatasource {

    override suspend fun getCharacters(
        offset: Int?,
        isPaginated: Boolean
    ): State<CharactersEntity> {
        val localData = local.getAllCharacters()
        return if (localData.isNotNullOrEmpty() && !isPaginated) {
            Success(CharactersEntity.fromList(localData.orEmpty()))
        } else {
            getCharactersFromService(offset)
        }
    }

    override suspend fun getCharacter(id: Int?): State<CharacterEntity> {
        return getCharacterDetailFromService(id)
    }

    private suspend fun getCharactersFromService(offset: Int?): State<CharactersEntity> {
        return if (networkTools.hasInternetConnection()) {
            service.getCharacters(offset, 10).run {
                if (isSuccessful && body() != null) {
                    val data = body()!!.data
                    saveCharactersToLocal(data.results.orEmpty())
                    Success(data)
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

    private suspend fun getCharacterDetailFromService(id: Int?): State<CharacterEntity> {
        return if (networkTools.hasInternetConnection()) {
            service.getCharacter(id).run {
                if (isSuccessful && body() != null) {
                    val singleItem = body()!!.data.results?.first() ?: CharacterEntity.empty()
                    Success(singleItem)
                } else {
                    Error(ServerError(code()))
                }
            }
        } else {
            Error(NetworkConnection)
        }
    }
}
