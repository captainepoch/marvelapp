package com.adolfo.marvel.features.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adolfo.characters.core.utils.CharactersConstants
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.usecases.GetCharacters
import com.adolfo.characters.domain.usecases.GetCharacters.Params
import com.adolfo.core.exception.Failure
import com.adolfo.core.functional.Either
import com.adolfo.core.functional.Either.Right
import com.adolfo.core_testing.CoroutineTestRule
import com.adolfo.marvel.features.character.view.ui.models.CharactersScreenState
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be instance of`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharactersViewModel
    private val getCharacters = mockk<GetCharacters>()

    @Before
    fun setUp() {
        viewModel = CharactersViewModel(getCharacters)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should emit get characters`(): Unit = runBlocking {
        val channel = Channel<Either<Failure, CharactersView>>()
        val flow = channel.consumeAsFlow()

        coEvery {
            getCharacters.invoke(
                Params(
                    0,
                    false,
                    CharactersConstants.Services.DEFAULT_CHARACTERS_LIMIT
                )
            )
        } returns flow

        val mockResponse = Right(
            CharactersView(
                listOf(),
                isFullEmpty = false,
                isPaginationEmpty = false
            )
        )
        val job = launch {
            channel.send(mockResponse)
        }

        viewModel.getCharacters()

        coVerify {
            getCharacters.invoke(
                Params(
                    0,
                    false,
                    CharactersConstants.Services.DEFAULT_CHARACTERS_LIMIT
                )
            )
        }
        viewModel.characters.value.`should be instance of`<CharactersScreenState>()

        job.cancel()
    }
}
