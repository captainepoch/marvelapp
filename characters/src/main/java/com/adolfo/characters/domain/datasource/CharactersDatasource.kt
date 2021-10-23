package com.adolfo.characters.domain.datasource

import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.core.functional.State

interface CharactersDatasource {

    suspend fun getCharacters(offset: Int?, isPaginated: Boolean): State<CharactersEntity>
}
