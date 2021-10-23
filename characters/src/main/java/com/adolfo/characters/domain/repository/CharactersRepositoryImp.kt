package com.adolfo.characters.domain.repository

import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.domain.datasource.CharactersDatasource
import com.adolfo.core.functional.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharactersRepositoryImp(
    private val datasource: CharactersDatasource
) : CharactersRepository {

    override fun getCharacters(offset: Int?, isPaginated: Boolean) = flow {
        emit(datasource.getCharacters(offset, isPaginated))
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
