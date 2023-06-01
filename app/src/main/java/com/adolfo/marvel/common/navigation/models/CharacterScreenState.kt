package com.adolfo.marvel.common.navigation.models

import com.adolfo.core.functional.State.Error

data class CharacterScreenState(
    val isLoading: Boolean = false,
    val error: Error? = null,
    val character: CharacterDetailItemModelItemModel = CharacterDetailItemModelItemModel()
)
