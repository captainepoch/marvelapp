package com.adolfo.characters.data.models.data

import com.adolfo.core.extensions.Empty


data class CharacterThumbData(
    val path: String?,
    val extension: String?
) {

    companion object {

        fun empty() = CharacterThumbData(String.Empty, String.Empty)
    }
}
