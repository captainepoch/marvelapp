package com.adolfo.marvel.common.navigation.models

import com.adolfo.core.functional.State.Error

data class CharactersScreenState(
    val isLoading: Boolean = false,
    val error: Error? = null,
    val characters: List<CharacterItemModel> = emptyList()
)
