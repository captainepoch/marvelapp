package com.adolfo.characters.usecases

import com.adolfo.characters.data.models.data.toCharactersView
import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.data.models.entity.toCharacters
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.usecases.GetCharacters
import com.adolfo.characters.domain.usecases.GetCharacters.Params
import com.adolfo.core.exception.Failure
import com.adolfo.core.functional.Either
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetCharactersTest {

    private lateinit var useCase: GetCharacters
    private val repository = mockk<CharactersRepository>()

    @Before
    fun setUp() {
        useCase = GetCharacters(repository)
    }

    @After
    fun finishTest() {
        unmockkAll()
    }

    @Test
    fun `should get characters`() = runBlocking {
        val mockResponse: Either<Failure, CharactersView> = Either.Right(
            CharactersEntity.empty().toCharacters().toCharactersView()
        )

        coEvery {
            repository.getCharacters(0, false, 15)
        } returns mockResponse

        useCase.invoke(Params(0, false, 15)).collect {
            it.`should be instance of`<Either.Right<CharactersView>>()
        }
    }
}
