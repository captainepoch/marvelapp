package com.adolfo.characters.datasource

import com.adolfo.characters.data.datasource.CharactersDatasource
import com.adolfo.characters.data.datasource.CharactersDatasourceImp
import com.adolfo.characters.data.local.CharactersLocal
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.data.service.CharactersApi
import com.adolfo.characters.data.service.CharactersService
import com.adolfo.core.extensions.empty
import com.adolfo.core.functional.State.Success
import com.adolfo.core.network.NetworkTools
import com.adolfo.core.platform.ApiResponse
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Response

class CharactersDatasourceTest {

    private lateinit var networkTools: NetworkTools

    @Before
    fun setUp() {
        networkTools = mock {
            onBlocking { hasInternetConnection() } doReturn true
        }
    }

    @Test
    fun `should get characters from datasource success`() = runBlocking {
        val characters = CharactersEntity.empty()
        val response = ApiResponse(
            -1,
            String.empty(),
            String.empty(),
            String.empty(),
            String.empty(),
            characters,
            String.empty(),
        )

        val api = mock<CharactersApi> {
            onBlocking { getCharacters(0, 15) } doReturn Response.success(response)
        }
        api.getCharacters(0, 15).body() shouldBeEqualTo response

        val service = mock<CharactersService> {
            onBlocking { getCharacters(0, 15) } doReturn Response.success(response)
        }

        val local = mock<CharactersLocal> {
            onBlocking { getAllCharacters() } doReturn characters.results
        }

        val datasource: CharactersDatasource = CharactersDatasourceImp(networkTools, service, local)

        val data = datasource.getCharacters(0, true, 15)
        data.`should be instance of`<Success<CharactersView>>()
        if (data is Success<CharactersView>) {
            data.data shouldBeEqualTo characters.toCharacters().toCharactersView()
        }
    }

    @Test
    fun `should get character from datasource success`() = runBlocking {
        val characters = CharactersEntity.fromList(listOf(CharacterEntity.empty()))
        val response = ApiResponse(
            -1,
            String.empty(),
            String.empty(),
            String.empty(),
            String.empty(),
            characters,
            String.empty(),
        )

        val api = mock<CharactersApi> {
            onBlocking { getCharacter(0) } doReturn Response.success(response)
        }
        api.getCharacter(0).body() shouldBeEqualTo response

        val service = mock<CharactersService> {
            onBlocking { getCharacter(0) } doReturn Response.success(response)
        }

        val datasource: CharactersDatasource =
            CharactersDatasourceImp(networkTools, service, mock())

        val data = datasource.getCharacter(0)
        data.`should be instance of`<Success<CharactersView>>()
        if (data is Success<CharacterView>) {
            data.data shouldBeEqualTo characters.toCharacters().toCharactersView().results.first()
        }
    }
}
