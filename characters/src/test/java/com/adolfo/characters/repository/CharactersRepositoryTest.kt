package com.adolfo.characters.repository

import com.adolfo.characters.data.datasource.CharactersDatasource
import com.adolfo.characters.data.datasource.CharactersDatasourceImp
import com.adolfo.characters.data.local.CharactersLocal
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.service.CharactersService
import com.adolfo.core.functional.State.Success
import com.adolfo.core.network.NetworkTools
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be equal to`
import org.junit.Before
import org.junit.Test

class CharactersRepositoryTest {

    private lateinit var dataSource: CharactersDatasource
    private val service = mockk<CharactersService>()
    private val local = mockk<CharactersLocal>()

    @Before
    fun setUp() {
        val networkTools = mockk<NetworkTools>()
        every {
            networkTools.hasInternetConnection()
        } returns true
        dataSource = CharactersDatasourceImp(networkTools, service, local)
    }

    @Test
    fun `should get characters from local source success`(): Unit = runBlocking {
        val mockResponse = listOf(CharacterEntity.empty())

        every {
            local.getAllCharacters()
        } returns mockResponse

        val result = dataSource.getCharacters(0, false, 10)
        result.`should be equal to`(Success(CharactersEntity.fromList(mockResponse)))
    }
}
