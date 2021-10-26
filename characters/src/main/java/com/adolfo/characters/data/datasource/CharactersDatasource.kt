package com.adolfo.characters.data.datasource

import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.core.functional.State

interface CharactersDatasource {

    suspend fun getCharacters(
        offset: Int?,
        isPaginated: Boolean,
        limit: Int?
    ): State<CharactersEntity>

    suspend fun getCharacter(id: Int?): State<CharacterEntity>
}
