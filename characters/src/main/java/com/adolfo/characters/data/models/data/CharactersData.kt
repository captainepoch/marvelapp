package com.adolfo.characters.data.models.data

import com.adolfo.characters.data.models.view.CharactersView

data class CharactersData(
    val id: Int?,
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<CharacterData>?
) {

    fun toCharactersView() = CharactersView(
        //offset ?: -1,
        results?.map { it.toCharacterView() } ?: listOf()
    )
}
