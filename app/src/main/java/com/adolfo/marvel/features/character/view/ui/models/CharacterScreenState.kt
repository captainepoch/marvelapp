package com.adolfo.marvel.features.character.view.ui.models

import com.adolfo.core.functional.State.Error

data class CharacterScreenState(
    val isLoading: Boolean = true,
    val error: Error? = null,
    val character: CharacterDetailItemModel = CharacterDetailItemModel()
)
