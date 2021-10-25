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

    companion object {
        fun empty(): CharactersEntity =
            CharactersEntity(-1, -1, -1, -1, -1, listOf())

        fun fromList(list: List<CharacterEntity>): CharactersEntity =
            CharactersEntity(-1, -1, -1, -1, -1, list)
    }

    fun toCharacters() = CharactersData(
        id, offset, limit, total, count, results?.map { it.toCharacter() }
    )
}
