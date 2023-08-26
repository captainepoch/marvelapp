package com.adolfo.characters.datasource

import com.adolfo.characters.data.datasource.CharactersDatasource
import com.adolfo.characters.data.datasource.CharactersDatasourceImp
import com.adolfo.characters.data.local.CharactersLocal
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.service.CharactersApi
import com.adolfo.characters.data.service.CharactersService
import com.adolfo.core.extensions.Empty
import com.adolfo.core.functional.State.Success
import com.adolfo.core.network.NetworkTools
import com.adolfo.core.platform.ApiResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CharactersDatasourceTest {

    private val networkTools = mockk<NetworkTools>()

    @Before
    fun setUp() {
        every {
            networkTools.hasInternetConnection()
        } returns true
    }

    @Test
    fun `should get characters from datasource success`() = runBlocking {
        val characters = CharactersEntity.empty()
        val response = ApiResponse(
            -1,
            String.Empty,
            String.Empty,
            String.Empty,
            String.Empty,
            characters,
            String.Empty,
        )

        val api = mockk<CharactersApi>()
        coEvery {
            api.getCharacters(0, 15)
        } returns Response.success(response)
        api.getCharacters(0, 15).body() shouldBeEqualTo response

        val service = mockk<CharactersService>()
        coEvery {
            service.getCharacters(0, 15)
        } returns Response.success(response)

        val local = mockk<CharactersLocal>()
        coEvery {
            local.getAllCharacters()
        } returns characters.results

        coEvery {
            local.saveCharacters(characters.results!!)
        } returns Unit

        val datasource: CharactersDatasource = CharactersDatasourceImp(networkTools, service, local)
        val data = datasource.getCharacters(0, true, 15)

        data.`should be instance of`<Success<CharactersEntity>>()
        if (data is Success<CharactersEntity>) {
            data.data shouldBeEqualTo characters
        }
    }

    @Test
    fun `should get character from datasource success`() = runBlocking {
        val character = CharacterEntity.empty()
        val characters = CharactersEntity.fromList(listOf(character))
        val response = ApiResponse(
            -1,
            String.Empty,
            String.Empty,
            String.Empty,
            String.Empty,
            characters,
            String.Empty,
        )

        val api = mockk<CharactersApi>()
        coEvery {
            api.getCharacter(0)
        } returns Response.success(response)
        api.getCharacter(0).body() shouldBeEqualTo response

        val service = mockk<CharactersService>()
        coEvery {
            service.getCharacter(0)
        } returns Response.success(response)

        val datasource: CharactersDatasource =
            CharactersDatasourceImp(networkTools, service, mockk())

        val data = datasource.getCharacter(0)
        data.`should be instance of`<Success<CharacterEntity>>()
        if (data is Success<CharacterEntity>) {
            data.data shouldBeEqualTo character
        }
    }
}
