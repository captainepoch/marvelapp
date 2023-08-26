package com.adolfo.characters.core.utils

sealed class CharactersConstants {

    data object Services : CharactersConstants() {
        const val DEFAULT_CHARACTERS_LIMIT = 15
    }

    data object Database : CharactersConstants() {
        const val DATABASE_NAME = "characters"
    }
}
