package com.adolfo.characters.domain.repository

import com.adolfo.characters.data.models.data.CharactersData
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.core.exception.Failure
import com.adolfo.core.functional.Either
import com.adolfo.core.functional.State
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getCharacters(
        offset: Int?,
        isPaginated: Boolean,
        limit: Int?
    ): Flow<Either<Failure, CharactersView>>

    suspend fun getCharacter(id: Int?): Flow<State<CharacterView>>
}
