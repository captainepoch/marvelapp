package com.adolfo.marvel.features.character.view.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.usecases.GetCharacters
import com.adolfo.core.extensions.cancelIfActive
import com.adolfo.core.functional.State.Error
import com.adolfo.core.functional.State.Success
import com.adolfo.marvel.common.navigation.models.CharacterItemModel
import com.adolfo.marvel.common.navigation.models.CharactersScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharactersViewModelCompose(
    private val getCharacters: GetCharacters
) : ViewModel() {

    private var charactersJob: Job? = null

    private val _characters = mutableStateOf(CharactersScreenState())
    val characters: State<CharactersScreenState>
        get() = _characters

    private val offset: Int
        get() = _characters.value.characters.size

    init {
        getCharacters()
    }

    fun getCharacters(isPaginated: Boolean = false) {
        charactersJob?.cancelIfActive()
        charactersJob = viewModelScope.launch {
            getCharacters(GetCharacters.Params(offset, isPaginated))
                .onStart { /*showLoader(true)*/ }
                .onCompletion { /*showLoader(false)*/ }
                .catch {/* failure ->
                    if (isPaginated) {
                        customErrorLiveData.value = Event(CustomError(CustomError.PAGINATION_ERROR))
                    } else {
                        handleFailure(Throwable(failure))
                    }*/
                }
                .collect { result ->
                    when (result) {
                        is Success<CharactersView> -> {
                            val results = result.data.results

                            _characters.value = _characters.value.copy(
                                characters = results.map { CharacterItemModel(it.name) }
                            )
                            /*val list = charactersLiveData.value?.results.orEmpty().toMutableList()
                            val results = state.data.results

                            if (results.isNullOrEmpty() && (offset == 0)) {
                                charactersLiveData.value =
                                    CharactersView(listOf(), isFullEmpty = true)
                            } else if (results.isNullOrEmpty() && isPaginated) {
                                charactersLiveData.value =
                                    CharactersView(listOf(), isPaginationEmpty = true)
                            } else {
                                list.addAll(results)
                                charactersLiveData.value = CharactersView(list)
                            }*/
                        }
                        is Error -> {
                            Unit
                            /*if (isPaginated) {
                                customErrorLiveData.value =
                                    Event(CustomError(CustomError.PAGINATION_ERROR))
                            } else {
                                handleFailure(state.failure)
                            }*/
                        }
                    }
                }
        }
    }
}
