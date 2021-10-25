package com.adolfo.marvel.features.character.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.core.exception.Failure.Throwable
import com.adolfo.core.extensions.cancelIfActive
import com.adolfo.core.functional.State.Error
import com.adolfo.core.functional.State.Success
import com.adolfo.marvel.common.platform.AppConstants
import com.adolfo.marvel.common.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterViewModel(
    stateHandle: SavedStateHandle,
    private val getCharacterDetail: GetCharacterDetail
) : BaseViewModel() {

    private var getCharacterJob: Job? = null

    private val characterDetail = stateHandle.getLiveData<CharacterView>(
        AppConstants.LiveData.CHARACTERS_VM
    )
    val character: LiveData<CharacterView> get() = characterDetail

    fun getCharacterDetail(id: Int?) {
        getCharacterJob?.cancelIfActive()
        getCharacterJob = viewModelScope.launch {
            getCharacterDetail.execute(GetCharacterDetail.Params(id))
                .onStart { showLoader(true) }
                .onCompletion { showLoader(false) }
                .catch { failure ->
                    handleFailure(Throwable(failure))
                }
                .collect { state ->
                    when (state) {
                        is Success<CharacterView> -> {
                            characterDetail.value = state.data
                        }
                        is Error ->
                            handleFailure(state.failure)
                    }
                }
        }
    }
}
