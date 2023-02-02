package com.adolfo.marvel.common.navigation.models

sealed class CharacterState<out T> {

    object Loading : CharacterState<Nothing>()
    object Error : CharacterState<Nothing>()
    data class CharacterScreenState(
        val list:List<Any>
    ) : CharacterState<Nothing>()
}
