package com.adolfo.characters.data.models.entity

import com.adolfo.characters.data.models.data.CharacterData
import com.adolfo.core.extensions.empty

data class CharacterEntity(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val thumbnail: CharacterThumbEntity?
) {

    companion object {

        fun empty() = CharacterEntity(
            -1,
            String.empty(),
            String.empty(),
            String.empty(),
            String.empty(),
            CharacterThumbEntity.empty()
        )
    }

    fun toCharacter() = CharacterData(
        id,
        name,
        description,
        modified,
        resourceURI,
        thumbnail?.toCharacterThumbData()
    )
}
