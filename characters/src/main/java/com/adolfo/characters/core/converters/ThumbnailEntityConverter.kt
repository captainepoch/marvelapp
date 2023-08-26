package com.adolfo.characters.core.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.adolfo.characters.data.models.entity.CharacterThumbEntity
import com.adolfo.core.extensions.Empty
import com.google.gson.Gson

@ProvidedTypeConverter
object ThumbnailEntityConverter {

    private val gson = Gson()

    @TypeConverter
    fun toThumbnail(data: String?): CharacterThumbEntity {
        if (!data.isNullOrEmpty() || !data.isNullOrBlank()) {
            return gson.fromJson(
                data,
                CharacterThumbEntity::class.java
            )
        }

        return CharacterThumbEntity.empty()
    }

    @TypeConverter
    fun fromThumbnail(thumbnail: CharacterThumbEntity?): String {
        if (thumbnail != null) {
            val path = thumbnail.path
            val ext = thumbnail.extension

            if (!path.isNullOrEmpty() || !path.isNullOrBlank() ||
                !ext.isNullOrEmpty() || !ext.isNullOrBlank()
            ) {
                return gson.toJson(thumbnail)
            }
        }

        return String.Empty
    }
}
