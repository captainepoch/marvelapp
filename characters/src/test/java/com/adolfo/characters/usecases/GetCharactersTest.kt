package com.adolfo.characters.usecases

import com.adolfo.characters.data.datasource.CharactersDatasourceImp
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.repository.CharactersRepositoryImp
import com.adolfo.core.functional.State.Success
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class GetCharactersTest {

    private lateinit var repository: CharactersRepository

    @Test
    fun `should get characters`() = runBlocking {
        val mockResponse = Success(CharactersEntity.empty())

        val dataSource = mockk<CharactersDatasourceImp>()
        coEvery {
            dataSource.getCharacters(0, false, 15)
        } returns mockResponse

        repository = CharactersRepositoryImp(dataSource)

        val flow = repository.getCharacters(0, false, 15)
        flow.collect { result ->
            result.`should be instance of`<Success<CharactersView>>()
            when (result) {
                is Success<CharactersView> -> {
                    result.data shouldBeEqualTo mockResponse.data.toCharacters().toCharactersView()
                }

                else -> {}
            }
        }
    }
}
