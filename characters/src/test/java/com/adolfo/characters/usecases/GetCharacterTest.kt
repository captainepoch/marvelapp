package com.adolfo.characters.usecases

import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.data.datasource.CharactersDatasourceImp
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.repository.CharactersRepositoryImp
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.core.functional.State.Success
import com.adolfo.core_testing.UnitTest
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

class GetCharacterTest : UnitTest() {

    private lateinit var repository: CharactersRepository
    private lateinit var getCharacters: GetCharacterDetail

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `should get characters`() = runBlocking {
        val mockResponse = Success(CharacterEntity.empty().toCharacter().toCharacterView())

        val dataSource = mock<CharactersDatasourceImp> {
            onBlocking { getCharacter(0) } doReturn mockResponse
        }

        repository = CharactersRepositoryImp(dataSource)
        getCharacters = GetCharacterDetail(repository)

        val flow = repository.getCharacter(0)
        flow.collect { result ->
            result.`should be instance of`<Success<CharactersEntity>>()
            when (result) {
                is Success<CharacterView> -> {
                    result.data shouldBeEqualTo mockResponse.data
                }
            }
        }
    }
}
