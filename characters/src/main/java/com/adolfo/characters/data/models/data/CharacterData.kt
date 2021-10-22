package com.adolfo.characters.data.models.data

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
        getThumbnailUrl()
    )

    private fun getThumbnailUrl(): String {
        val url = thumbnail?.path
        val ext = thumbnail?.extension

        if (url?.isEmpty() == true || url?.isBlank() == true) {
            return String.empty()
        }

        if (ext?.isEmpty() == true || url?.isBlank() == true) {
            return String.empty()
        }

        return "$url.$ext"
    }
}
