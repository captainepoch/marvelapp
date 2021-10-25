package com.adolfo.characters.domain.datasource

import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.core.functional.State

interface CharactersDatasource {

    suspend fun getCharacters(offset: Int?, isPaginated: Boolean): State<CharactersView>

    suspend fun getCharacter(id: Int?): State<CharacterView>
}
