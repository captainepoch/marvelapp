package com.adolfo.marvel.common.di

import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModelCompose
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModelCompose
import org.koin.dsl.module

val viewModels = module {
    factory { CharactersViewModelCompose(get()) }
    factory { CharacterViewModelCompose(get(), get()) }
}
