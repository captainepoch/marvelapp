package com.adolfo.marvel.features.character.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.usecases.GetCharacters
import com.adolfo.core.exception.Failure
import com.adolfo.core.exception.Failure.CustomError
import com.adolfo.core.extensions.cancelIfActive
import com.adolfo.core.functional.State.Error
import com.adolfo.core.functional.State.Success
import com.adolfo.marvel.common.navigation.models.CharacterItemModel
import com.adolfo.marvel.common.navigation.models.CharactersScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModelCompose(
    private val getCharacters: GetCharacters
) : ViewModel() {

    private var charactersJob: Job? = null

    private val _characters = MutableStateFlow(CharactersScreenState())
    val characters: StateFlow<CharactersScreenState>
        get() = _characters

    private val offset: Int
        get() = _characters.value.characters.size

    init {
        getCharacters()
    }

    fun getCharacters(isPaginated: Boolean = false) {
        // TODO: FUTURE USE
        val paginatedRequest = if (isPaginated) {
            isPaginated
        } else {
            _characters.value.characters.isNotEmpty()
        }

        charactersJob?.cancelIfActive()
        charactersJob = viewModelScope.launch {
            getCharacters(GetCharacters.Params(offset, isPaginated))
                .onStart {
                    _characters.update { state ->
                        state.copy(isLoading = true)
                    }
                }
                .catch { failure ->
                    if (isPaginated) {
                        //customErrorLiveData.value = Event(CustomError(CustomError.PAGINATION_ERROR))
                    } else {
                        _characters.update { state ->
                            state.copy(
                                error = Error(Failure.Throwable(failure))
                            )
                        }
                    }
                }
                .collect { result ->
                    when (result) {
                        is Success<CharactersView> -> {
                            val results = result.data.results

                            if (results.isEmpty() && (offset == 0)) {
                                _characters.update { state ->
                                    state.copy(
                                        isLoading = false
                                    )
                                }
                            } else if (results.isEmpty() && isPaginated) {
                                _characters.update { state ->
                                    state.copy(
                                        isLoading = false,
                                        finishPagination = true
                                    )
                                }
                            } else {
                                _characters.update { state ->
                                    state.copy(
                                        isLoading = false,
                                        characters = state.characters.plus(
                                            results.map {
                                                CharacterItemModel(
                                                    it.id,
                                                    it.name,
                                                    it.thumbnail
                                                )
                                            }
                                        )
                                    )
                                }
                            }
                        }

                        is Error -> {
                            if (isPaginated) {
                                _characters.update { state ->
                                    state.copy(
                                        isLoading = false,
                                        error = Error(CustomError(CustomError.PAGINATION_ERROR))
                                    )
                                }
                            } else {
                                _characters.update { state ->
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
}
