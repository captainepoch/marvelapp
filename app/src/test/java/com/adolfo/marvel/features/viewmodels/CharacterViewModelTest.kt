package com.adolfo.marvel.features.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.core.functional.State.Success
import com.adolfo.core_testing.CoroutineTestRule
import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
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
    private val characterObserver = mockk<Observer<CharacterView>>()

    @Before
    fun setUp() {
        viewModel = CharacterViewModel(SavedStateHandle(), getCharacterDetail)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should get character`() = runTest {
        viewModel.character.observeForever(characterObserver)
        val expectedResult = Success(CharacterEntity.empty().toCharacter().toCharacterView())

        val flow = MutableStateFlow(expectedResult)
        every {
            getCharacterDetail.execute(GetCharacterDetail.Params(0))
        } returns flow

        viewModel.getCharacterDetail(0)

        verify {
            characterObserver.onChanged(expectedResult.data)
        }
    }
}
