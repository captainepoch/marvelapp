package com.adolfo.marvel.features.character.view.ui.models

import com.adolfo.core.functional.State.Error
import com.adolfo.marvel.features.character.view.ui.models.CharactersScreenState.LoadingType.NORMAL

data class CharactersScreenState(
    val isLoading: LoadingType = NORMAL,
    val error: Error? = null,
    val finishPagination: Boolean = false,
    val characters: List<CharacterItemModel> = listOf()
) {

    enum class LoadingType {
        NORMAL,
        PAGINATION,
        NONE;

        companion object {

            fun getPaginationLoader(isPagination: Boolean = false): LoadingType {
                return if (isPagination) {
                    PAGINATION
                } else {
                    NORMAL
                }
            }
        }
    }
}
