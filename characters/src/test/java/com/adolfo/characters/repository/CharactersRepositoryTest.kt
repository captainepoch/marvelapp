package com.adolfo.characters.repository

import com.adolfo.characters.data.local.CharactersLocal
import com.adolfo.characters.data.models.data.toCharactersView
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.models.entity.toCharacters
import com.adolfo.characters.data.service.CharactersService
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.repository.CharactersRepositoryImp
import com.adolfo.core.extensions.Empty
import com.adolfo.core.functional.Either
import com.adolfo.core.network.NetworkTools
import com.adolfo.core.platform.ApiResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be equal to`
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CharactersRepositoryTest {

    private lateinit var repository: CharactersRepository
    private val service = mockk<CharactersService>()
    private val local = mockk<CharactersLocal>()

    @Before
    fun setUp() {
        val networkTools = mockk<NetworkTools>()
        every {
            networkTools.hasInternetConnection()
        } returns true

        repository = CharactersRepositoryImp(networkTools, service, local)
    }

    @Test
    fun `should get characters from local source success`(): Unit = runBlocking {
        val mockResponse = listOf(CharacterEntity.empty())

        coEvery {
            local.getAllCharacters()
        } returns mockResponse

        val result = repository.getCharacters(0, false, 10)
        result.`should be equal to`(
            Either.Right(
                CharactersEntity.fromList(mockResponse).toCharacters().toCharactersView()
            )
        )
    }

    @Test
    fun `should get characters from service source success`(): Unit = runBlocking {
        val mockResponse = Response.success(
            ApiResponse(
                0, String.Empty, String.Empty, String.Empty, String.Empty,
                CharactersEntity.empty(), String.Empty
            )
        )

        coEvery {
            local.getAllCharacters()
        } returns null

        coEvery {
            local.saveCharacters(any())
        } returns Unit

        coEvery {
            service.getCharacters(0, 10)
        } returns mockResponse

        val result = repository.getCharacters(0, false, 10)
        result.`should be equal to`(
            Either.Right(
                CharactersEntity.fromList(mockResponse.body()!!.data.results!!).toCharacters()
                    .toCharactersView()
            )
        )
    }
}
