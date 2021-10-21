package com.adolfo.characters.domain.repository

import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.core.functional.State
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharacters(): Flow<State<CharactersEntity>>

    fun getCharacter(id: Int)
}
