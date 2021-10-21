package com.adolfo.characters.data.models.entity

import com.adolfo.characters.data.models.data.CharactersData

data class CharactersEntity(
    val id: Int?,
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<CharacterEntity>?
) {

    fun toCharacters() = CharactersData(
        id, offset, limit, total, count, results?.map { it.toCharacter() }
    )
}
