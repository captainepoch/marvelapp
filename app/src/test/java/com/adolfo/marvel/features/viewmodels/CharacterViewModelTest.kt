package com.adolfo.marvel.features.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.core.exception.Failure
import com.adolfo.core.functional.Either
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Success
import com.adolfo.core_testing.CoroutineTestRule
import com.adolfo.marvel.features.character.view.ui.models.CharacterScreenState
import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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
class CharacterViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharacterViewModel
    private val getCharacterDetail = mockk<GetCharacterDetail>()
    private val savedStateHandle = mockk<SavedStateHandle>()

    @Before
    fun setUp() {
        every {
            savedStateHandle.get<Int>("id")
        } returns 1

        viewModel = CharacterViewModel(getCharacterDetail, savedStateHandle)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should emit get characters`() = runBlocking {
        val channel = Channel<Either<Failure, CharacterView>>()
        val flow = channel.consumeAsFlow()

        coEvery {
            getCharacterDetail.invoke(GetCharacterDetail.Params(1))
        } returns flow

        val mockResponse = Either.Right(
            CharacterView(1, "", "", "", "", "")
        )
        val job = launch {
            channel.send(mockResponse)
        }

        viewModel.getCharacterDetail(1)
        coVerify {
            getCharacterDetail.invoke(GetCharacterDetail.Params(1))
        }

        viewModel.character.value.`should be instance of`<CharacterScreenState>()

        job.cancel()
    }
}
