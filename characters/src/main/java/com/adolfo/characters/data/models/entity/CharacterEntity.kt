package com.adolfo.characters.data.models.entity

import com.adolfo.characters.data.models.data.CharacterData

data class CharacterEntity(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val thumbnail: CharacterThumbEntity?
) {

    fun toCharacter() = CharacterData(
        id,
        name,
        description,
        modified,
        resourceURI,
        thumbnail?.toCharacterThumbData()
    )
}
