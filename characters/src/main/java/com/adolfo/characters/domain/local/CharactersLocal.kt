package com.adolfo.characters.domain.local

import com.adolfo.characters.data.models.entity.CharacterEntity

interface CharactersLocal {

    fun getAllCharacters(): List<CharacterEntity>

    fun saveCharacters(characters: List<CharacterEntity>)
}
