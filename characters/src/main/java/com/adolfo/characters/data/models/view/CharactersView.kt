package com.adolfo.characters.data.models.view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersView(
    val results: List<CharacterView>,
    val isFullEmtpy: Boolean = false,
    val isPaginationEmpty: Boolean = false
) : Parcelable
