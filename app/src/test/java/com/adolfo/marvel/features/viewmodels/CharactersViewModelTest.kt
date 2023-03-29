package com.adolfo.marvel.features.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.usecases.GetCharacters
import com.adolfo.core.exception.Failure
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Success
import com.adolfo.core_testing.CoroutineTestRule
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharactersViewModel

    private var repository = mockk<CharactersRepository>()
    private var getCharacters = mockk<GetCharacters>()

    private val charactersObserver = mockk<Observer<CharactersView>>()
    private val errorObserver = mockk<Observer<Failure>>()

    @Before
    fun setup() {
        getCharacters = GetCharacters(repository)

        viewModel = CharactersViewModel(SavedStateHandle(), getCharacters).apply {
            characters.observeForever(charactersObserver)
            failure.observeForever(errorObserver)
        }
    }

    @Test
    fun `should emit get characters`() = runTest {
        val expectedResult = Success(
            CharactersView(listOf(), isFullEmpty = true)
        )
        val expectedError = Failure.Throwable(Throwable())

        val channel = Channel<State<CharactersView>>()
        val flow = channel.consumeAsFlow()

        coEvery {
            repository.getCharacters(0, false, 15)
        } returns flow

        launch {
            channel.send(expectedResult)
            channel.close(expectedError.throwable)
        }

        viewModel.getCharacters(false)

        coVerify {
            charactersObserver.onChanged(expectedResult.data)
        }
        coVerify {
            errorObserver.onChanged(expectedError)
        }
    }
}
