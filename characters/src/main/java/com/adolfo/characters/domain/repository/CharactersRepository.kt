package com.adolfo.characters.domain.repository

import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.core.functional.State
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharacters(offset: Int?, isPaginated: Boolean): Flow<State<CharactersView>>

    fun getCharacter(id: Int?): Flow<State<CharacterView>>
}
