package com.adolfo.characters.data.models.data

import com.adolfo.core.extensions.empty

data class CharacterThumbData(
    val path: String?,
    val extension: String?
) {

    companion object {

        fun empty() = CharacterThumbData(String.empty(), String.empty())
    }
}
