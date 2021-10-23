package com.adolfo.characters.domain.repository

import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.service.CharactersService
import com.adolfo.characters.domain.datasource.CharactersDatasource
import com.adolfo.core.exception.Failure.NetworkConnection
import com.adolfo.core.exception.Failure.ServerError
import com.adolfo.core.exception.Failure.Throwable
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Error
import com.adolfo.core.functional.State.Success
import com.adolfo.core.network.NetworkTools
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CharactersRepositoryImp(
    private val datasource: CharactersDatasource
) : CharactersRepository {

    override fun getCharacters(offset: Int?) = flow {
        emit(datasource.getCharacters(offset))
    }

    override fun getCharacter(id: Int?): Flow<State<CharacterEntity>> {
        TODO("Not yet implemented")
    }

    /*override fun getCharacter(id: Int?) = flow {
        emit(getCharacterDetailFromService(id))
    }.catch { emit(Error(Throwable(it))) }*/

    /*private suspend fun getCharactersFromService(offset: Int?): State<CharactersEntity> {
        return if (networkTools.hasInternetConnection()) {
            service.getCharacters(offset, 10).run {
                if (isSuccessful && body() != null) {
                    Success(body()!!.data)
                } else {
                    Error(ServerError(code()))
                }
            }
        } else {
            Error(NetworkConnection)
        }
    }*/

    /*private suspend fun getCharacterDetailFromService(id: Int?): State<CharacterEntity> {
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
    }*/
}
