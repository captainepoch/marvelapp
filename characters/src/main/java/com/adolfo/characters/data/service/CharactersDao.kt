package com.adolfo.characters.data.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.adolfo.characters.core.utils.CharactersConstants
import com.adolfo.characters.data.models.entity.CharacterEntity

@Dao
interface CharactersDao {

    @Query("SELECT * FROM CharacterEntity")
    fun getAll(): List<CharacterEntity>

    @Insert
    fun insertAll(character: List<CharacterEntity>)
}
