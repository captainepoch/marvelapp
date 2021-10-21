package com.adolfo.marvel.common.di

import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModel
import org.koin.dsl.module

val viewModels = module {
    factory { CharacterViewModel(get(), get()) }
}
