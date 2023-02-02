package com.adolfo.marvel.features.character.view.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.adolfo.marvel.common.navigation.models.CharacterState
import timber.log.Timber
import timber.log.Timber.Forest

class CharactersViewModelCompose : ViewModel() {

    private val _characters: MutableState<CharacterState> = mutableStateOf(CharacterState.Loading)
    val characters: State<CharacterState>
        get() = _characters

    init {
        Timber.d("Init CharactersViewModelCompose")
    }
}
