package com.adolfo.characters.data.models.view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterView(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val resourceURI: String,
    val thumbnail: CharacterThumbView
) : Parcelable
