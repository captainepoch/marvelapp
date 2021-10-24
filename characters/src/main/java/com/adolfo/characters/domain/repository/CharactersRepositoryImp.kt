package com.adolfo.characters.domain.repository

import com.adolfo.characters.domain.datasource.CharactersDatasource
import kotlinx.coroutines.flow.flow

class CharactersRepositoryImp(
    private val datasource: CharactersDatasource
) : CharactersRepository {

    override fun getCharacters(offset: Int?, isPaginated: Boolean) = flow {
        emit(datasource.getCharacters(offset, isPaginated))
    }

    override fun getCharacter(id: Int?) = flow {
        emit(datasource.getCharacter(id))
    }
}
