package com.adolfo.marvel.common.di

import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModel
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModel
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModelCompose
import org.koin.dsl.module

val viewModels = module {
    factory { CharactersViewModel(get(), get()) }
    factory { CharacterViewModel(get(), get()) }

    factory { CharactersViewModelCompose(get()) }
}
