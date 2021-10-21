package com.adolfo.marvel.features.character.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.usecases.GetCharacters
import com.adolfo.core.extensions.cancelIfActive
import com.adolfo.core.functional.State.Success
import com.adolfo.core.interactor.UseCase
import com.adolfo.core.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val stateHandle: SavedStateHandle,
    private val getCharacters: GetCharacters
) : BaseViewModel() {

    private var charactersJob: Job? = null

    private val charactersLiveData = stateHandle.getLiveData<CharactersView>("characters")
    val characters: LiveData<CharactersView> get() = charactersLiveData

    init {
        getCharacters()
    }

    fun getCharacters() {
        charactersJob?.cancelIfActive()
        charactersJob = viewModelScope.launch {
            getCharacters(UseCase.None())
                .collect { state ->
                    when (state) {
                        is Success<CharactersEntity> -> {
                            charactersLiveData.value = state.data.toCharacters().toCharactersView()
                        }
                    }
                }
        }
    }
}
