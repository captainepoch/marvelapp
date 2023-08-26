package com.adolfo.characters.data.models.data

import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.core.extensions.Empty

data class CharacterData(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val thumbnail: CharacterThumbData?
) {

    internal fun getThumbnailUrl(): String {
        val url = thumbnail?.path
        val ext = thumbnail?.extension

        if (url?.isEmpty() == true || url?.isBlank() == true) {
            return String.Empty
        }

        if (ext?.isEmpty() == true || url?.isBlank() == true) {
            return String.Empty
        }

        return "$url.$ext"
    }
}

fun CharacterData.toCharacterView() = CharacterView(
    id ?: -1,
    name ?: String.Empty,
    description ?: String.Empty,
    modified ?: String.Empty,
    resourceURI ?: String.Empty,
    getThumbnailUrl()
)
