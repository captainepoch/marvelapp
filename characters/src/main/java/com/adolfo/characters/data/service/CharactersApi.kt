package com.adolfo.characters.data.service

import com.adolfo.characters.core.utils.CharactersConstants
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.core.platform.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    private companion object {
        const val CHARACTERS = "/v1/public/characters"
        const val CHARACTER = "${CHARACTERS}/{id}"
    }

    @GET(CHARACTERS)
    suspend fun getCharacters(
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int? = CharactersConstants.Services.DEFAULT_CHARACTERS_LIMIT,
    ): Response<ApiResponse<CharactersEntity>>

    @GET(CHARACTER)
    suspend fun getCharacter(@Path("id") id: Int?)
            : Response<ApiResponse<CharactersEntity>>
}
