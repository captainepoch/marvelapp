package com.adolfo.characters.data.models.view

import android.os.Parcelable
import com.adolfo.core.extensions.empty
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterThumbView(
    val path: String,
    val extension: String
) : Parcelable {

    companion object {

        fun empty() = CharacterThumbView(String.empty(), String.empty())
    }
}
