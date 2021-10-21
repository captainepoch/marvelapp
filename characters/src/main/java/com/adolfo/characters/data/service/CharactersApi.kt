package com.adolfo.characters.data.service

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
        @Query("limit") limit: Int? = 5,
    ): Response<ApiResponse<CharactersEntity>>

    @GET(CHARACTER)
    suspend fun getCharacter(@Path("id") id: String?)
}
