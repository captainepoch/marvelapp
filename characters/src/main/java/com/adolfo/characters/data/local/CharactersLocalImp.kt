package com.adolfo.characters.data.local

import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.database.CharactersDatabase

class CharactersLocalImp(db: CharactersDatabase) : CharactersLocal {

    private val dao by lazy { db.getCharactersDao() }

    override fun getAllCharacters(): List<CharacterEntity>? {
        return dao.getAll()
    }

    override fun saveCharacters(characters: List<CharacterEntity>) {
        dao.insertAll(characters)
    }
}
