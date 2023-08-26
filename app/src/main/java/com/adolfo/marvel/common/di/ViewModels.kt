package com.adolfo.marvel.common.di

import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModelCompose
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModel
import org.koin.dsl.module

val viewModels = module {
    factory { CharactersViewModel(get()) }
    factory { CharacterViewModelCompose(get(), get()) }
}
