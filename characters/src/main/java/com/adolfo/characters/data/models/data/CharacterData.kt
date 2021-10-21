package com.adolfo.characters.data.models.data

import com.adolfo.characters.data.models.view.CharacterThumbView
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.core.extensions.empty

data class CharacterData(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val thumbnail: CharacterThumbData?
) {

    fun toCharacterView() = CharacterView(
        id ?: -1,
        name ?: String.empty(),
        description ?: String.empty(),
        modified ?: String.empty(),
        resourceURI ?: String.empty(),
        thumbnail?.toCharacterThumbView() ?: CharacterThumbView.empty()
    )
}
