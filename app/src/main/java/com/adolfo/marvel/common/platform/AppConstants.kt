package com.adolfo.marvel.common.platform

sealed class AppConstants {

    object LiveData : AppConstants() {
        const val CHARACTERS_VM = "characters"
        const val CHARACTER_VM = "character"
    }
}
