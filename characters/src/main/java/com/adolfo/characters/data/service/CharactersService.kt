package com.adolfo.characters.data.service

import retrofit2.Retrofit

class CharactersService(private val retrofit: Retrofit) : CharactersApi {

    private val api: CharactersApi by lazy { retrofit.create(CharactersApi::class.java) }

    override suspend fun getCharacters(offset: Int?, limit: Int?) = api.getCharacters(offset, limit)

    override suspend fun getCharacter(id: Int?) = api.getCharacter(id)
}
