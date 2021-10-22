package com.adolfo.marvel.features.character.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.core.extensions.cancelIfActive
import com.adolfo.core.functional.State.Success
import com.adolfo.marvel.common.platform.AppConstants
import com.adolfo.marvel.common.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterViewModel(
    stateHandle: SavedStateHandle,
    private val getCharacterDetail: GetCharacterDetail
) : BaseViewModel() {

    private var getCharacterJob: Job? = null

    private val characterDetail: MutableLiveData<CharacterView> =
        stateHandle.getLiveData(AppConstants.LiveData.CHARACTER_VM)
    val character: LiveData<CharacterView> get() = characterDetail

    fun getCharacterDetail(id: Int?) {
        getCharacterJob?.cancelIfActive()
        getCharacterJob = viewModelScope.launch {
            getCharacterDetail.execute(GetCharacterDetail.Params(id))
                .collect { state ->
                    when (state) {
                        is Success<CharacterEntity> -> {
                            characterDetail.value = state.data.toCharacter().toCharacterView()
                        }
                        else -> {
                        }
                    }
                }
        }
    }
}
