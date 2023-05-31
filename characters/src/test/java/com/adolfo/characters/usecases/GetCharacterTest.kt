package com.adolfo.characters.usecases

import com.adolfo.characters.data.datasource.CharactersDatasourceImp
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.repository.CharactersRepositoryImp
import com.adolfo.core.functional.State.Success
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class GetCharacterTest {

    private lateinit var repository: CharactersRepository

    @Test
    fun `should get characters`() = runBlocking {
        val mockResponse = Success(CharacterEntity.empty())

        val dataSource = mockk<CharactersDatasourceImp>()
        coEvery {
            dataSource.getCharacter(0)
        } returns mockResponse

        repository = CharactersRepositoryImp(dataSource)

        val flow = repository.getCharacter(0)
        flow.collect { result ->
            result.`should be instance of`<Success<CharacterView>>()
            when (result) {
                is Success<CharacterView> -> {
                    result.data shouldBeEqualTo mockResponse.data.toCharacter().toCharacterView()
                }

                else -> {}
            }
        }
    }
}
