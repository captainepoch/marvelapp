package com.adolfo.characters.data.models.entity

import com.adolfo.characters.data.models.data.CharacterThumbData
import com.adolfo.core.extensions.empty

data class CharacterThumbEntity(
    val path: String?,
    val extension: String?
) {

    companion object {

        fun empty() = CharacterThumbEntity(String.empty(), String.empty())
    }

    fun toCharacterThumbData() = CharacterThumbData(path, extension)
}
