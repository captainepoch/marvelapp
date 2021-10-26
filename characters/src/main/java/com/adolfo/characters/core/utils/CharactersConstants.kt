package com.adolfo.characters.core.utils

sealed class CharactersConstants {

    object Services : CharactersConstants() {
        const val DEFAULT_CHARACTERS_LIMIT = 15
    }

    object BBDD : CharactersConstants() {
        const val DATABASE_NAME = "characters"
    }
}
