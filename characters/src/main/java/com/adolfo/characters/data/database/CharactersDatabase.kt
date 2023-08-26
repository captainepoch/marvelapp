package com.adolfo.characters.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adolfo.characters.core.converters.ThumbnailEntityConverter
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.database.dao.CharactersDao

@Database(entities = [CharacterEntity::class], version = 1)
@TypeConverters(ThumbnailEntityConverter::class)
abstract class CharactersDatabase : RoomDatabase() {

    abstract fun getCharactersDao(): CharactersDao
}
