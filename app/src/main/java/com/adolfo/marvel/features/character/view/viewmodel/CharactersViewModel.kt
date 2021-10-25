package com.adolfo.marvel.features.character.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.usecases.GetCharacters
import com.adolfo.core.extensions.cancelIfActive
import com.adolfo.core.functional.State.Success
import com.adolfo.marvel.common.platform.AppConstants
import com.adolfo.marvel.common.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharactersViewModel(
    stateHandle: SavedStateHandle,
    private val getCharacters: GetCharacters
) : BaseViewModel(stateHandle) {

    private val offset: Int
        get() = charactersLiveData.value?.results.orEmpty().size

    private var charactersJob: Job? = null

    private val charactersLiveData = stateHandle.getLiveData<CharactersView>(
        AppConstants.LiveData.CHARACTERS_VM
    )
    val characters: LiveData<CharactersView> get() = charactersLiveData

    init {
        getCharacters()
    }

    fun getCharacters(isPaginated: Boolean = false) {
        charactersJob?.cancelIfActive()
        charactersJob = viewModelScope.launch {
            getCharacters(GetCharacters.Params(offset, isPaginated))
                .onStart { showLoader(true) }
                .onCompletion { showLoader(false) }
                .collect { state ->
                    when (state) {
                        is Success<CharactersView> -> {
                            val list = charactersLiveData.value?.results.orEmpty().toMutableList()
                            list.addAll(state.data.results)
                            charactersLiveData.value = CharactersView(list)
                        }
                        else -> {
                        }
                    }
                }
        }
    }
}
