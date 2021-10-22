package com.adolfo.characters.core.di

import com.adolfo.characters.data.service.CharactersService
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.repository.CharactersRepositoryImp
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.characters.domain.usecases.GetCharacters
import org.koin.dsl.module

val charactersRepository = module {
    factory<CharactersRepository> { CharactersRepositoryImp(get(), get()) }
}

val charactersService = module {
    factory { CharactersService(get()) }
}

val charactersUseCases = module {
    factory { GetCharacters(get()) }
    factory { GetCharacterDetail(get()) }
}

val characters = listOf(
    charactersService,
    charactersRepository,
    charactersUseCases
)
