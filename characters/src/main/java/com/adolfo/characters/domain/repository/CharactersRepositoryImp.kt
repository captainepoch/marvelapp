package com.adolfo.characters.domain.repository

import com.adolfo.characters.data.datasource.CharactersDatasource
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Error
import com.adolfo.core.functional.State.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharactersRepositoryImp(
    private val datasource: CharactersDatasource
) : CharactersRepository {

    override fun getCharacters(offset: Int?, isPaginated: Boolean, limit: Int?) = flow {
        when (val data: State<CharactersEntity> =
            datasource.getCharacters(offset, isPaginated, limit)
        ) {
            is Success<CharactersEntity> -> emit(
                Success(
                    data.data.toCharacters().toCharactersView()
                )
            )
            is Error -> emit(Error(data.failure))
        }
    }.flowOn(Dispatchers.IO)

    override fun getCharacter(id: Int?) = flow {
        when (val data: State<CharacterEntity> = datasource.getCharacter(id)) {
            is Success<CharacterEntity> -> emit(
                Success(
                    data.data.toCharacter().toCharacterView()
                )
            )
            is Error -> emit(Error(data.failure))
        }
    }.flowOn(Dispatchers.IO)
}
