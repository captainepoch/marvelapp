package com.adolfo.marvel.features.character.view.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.core.exception.Failure
import com.adolfo.core.extensions.cancelIfActive
import com.adolfo.core.functional.State.Error
import com.adolfo.core.functional.State.Success
import com.adolfo.marvel.common.navigation.models.CharacterDetailItemModel
import com.adolfo.marvel.common.navigation.models.CharacterScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModelCompose(
    private val getCharacterDetail: GetCharacterDetail,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var getCharacterJob: Job? = null

    private val _character = MutableStateFlow(CharacterScreenState())
    val character: StateFlow<CharacterScreenState>
        get() = _character

    private val characterId: Int
        get() = savedStateHandle.get<Int>("id") ?: -1

    init {
        getCharacterDetail(characterId)
    }

    fun getCharacterDetail(id: Int? = characterId) {
        getCharacterJob?.cancelIfActive()
        getCharacterJob = viewModelScope.launch {
            getCharacterDetail(GetCharacterDetail.Params(id))
                .onStart {
                    _character.update { state ->
                        state.copy(isLoading = true)
                    }
                }
                .catch { failure ->
                    _character.update { state ->
                        state.copy(
                            isLoading = false,
                            error = Error(Failure.Throwable(failure))
                        )
                    }
                }
                .collect { result ->
                    when (result) {
                        is Success<CharacterView> -> {
                            _character.update { state ->
                                state.copy(
                                    isLoading = false,
                                    error = null,
                                    character = CharacterDetailItemModel(
                                        result.data.thumbnail,
                                        result.data.name,
                                        result.data.description
                                    )
                                )
                            }
                        }

                        is Error -> {
                            _character.update { state ->
                                state.copy(
                                    isLoading = false,
                                    error = Error(result.failure)
                                )
                            }
                        }
                    }
                }
        }
    }
}
