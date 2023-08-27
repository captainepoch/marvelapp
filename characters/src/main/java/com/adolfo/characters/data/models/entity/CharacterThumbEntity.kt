package com.adolfo.characters.data.models.entity

import com.adolfo.characters.data.models.data.CharacterThumbData
import com.adolfo.core.extensions.Empty

data class CharacterThumbEntity(
    val path: String?,
    val extension: String?
) {

    companion object {

        fun empty() = CharacterThumbEntity(String.Empty, String.Empty)
    }
}


fun CharacterThumbEntity.toCharacterThumbData() = CharacterThumbData(path, extension)
