package com.adolfo.characters.usecases

import com.adolfo.characters.data.models.data.toCharacterView
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.entity.toCharacter
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.characters.domain.usecases.GetCharacterDetail.Params
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

class GetCharacterDetailTest {

    private lateinit var useCase: GetCharacterDetail
    private val repository = mockk<CharactersRepository>()

    @Before
    fun setUp() {
        useCase = GetCharacterDetail(repository)
    }

    @After
    fun finishTest() {
        unmockkAll()
    }

    @Test
    fun `should get character by id`() = runBlocking {
        val mockResponse: Either<Failure, CharacterView> = Either.Right(
            CharacterEntity.empty().toCharacter().toCharacterView()
        )

        coEvery {
            repository.getCharacter(0)
        } returns mockResponse

        useCase.invoke(Params(0)).collect {
            it.`should be instance of`<Either.Right<CharacterView>>()
        }
    }
}
