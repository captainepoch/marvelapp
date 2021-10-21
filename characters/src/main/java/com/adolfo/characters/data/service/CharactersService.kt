package com.adolfo.characters.data.service

import retrofit2.Retrofit

class CharactersService(private val retrofit: Retrofit) : CharactersApi {

    private val api: CharactersApi by lazy { retrofit.create(CharactersApi::class.java) }

    override suspend fun getCharacters(limit: Int?) = api.getCharacters(limit)

    override suspend fun getCharacter(id: String?) {
    }
}
