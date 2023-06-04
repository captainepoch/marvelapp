package com.adolfo.marvel.features.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.usecases.GetCharacters
import com.adolfo.characters.domain.usecases.GetCharacters.Params
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Success
import com.adolfo.core_testing.CoroutineTestRule
import com.adolfo.marvel.features.character.view.ui.models.CharactersScreenState
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModelCompose
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
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

    private lateinit var viewModel: CharactersViewModelCompose
    private val getCharacters = mockk<GetCharacters>()

    @Before
    fun setUp() {
        viewModel = CharactersViewModelCompose(getCharacters)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should emit get characters`() = runBlocking {
        val channel = Channel<State<CharactersView>>()
        val flow = channel.consumeAsFlow()

        coEvery {
            getCharacters.invoke(Params(0, false, 15))
        } returns flow

        val mockResponse = Success(
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
            getCharacters.invoke(Params(0, false, 15))
        }

        viewModel.characters.value.`should be instance of`<CharactersScreenState>()

        job.cancel()
    }
}
