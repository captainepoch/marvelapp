package com.adolfo.characters.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adolfo.characters.data.models.entity.CharacterEntity

@Dao
interface CharactersDao {

    @Query("SELECT * FROM CharacterEntity ORDER BY name")
    fun getAll(): List<CharacterEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(character: List<CharacterEntity>)
}
