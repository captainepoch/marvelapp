package com.adolfo.characters.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.adolfo.characters.core.converters.ThumbnailEntityConverter
import com.adolfo.characters.data.models.data.CharacterData
import com.adolfo.core.extensions.Empty

@Entity
@TypeConverters(ThumbnailEntityConverter::class)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val thumbnail: CharacterThumbEntity?
) {

    companion object {

        fun empty() = CharacterEntity(
            -1,
            String.Empty,
            String.Empty,
            String.Empty,
            String.Empty,
            CharacterThumbEntity.empty()
        )
    }

    fun toCharacter() = CharacterData(
        id,
        name,
        description,
        modified,
        resourceURI,
        thumbnail?.toCharacterThumbData()
    )
}
