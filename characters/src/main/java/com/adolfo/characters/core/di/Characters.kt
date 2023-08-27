package com.adolfo.characters.core.di

import androidx.room.Room
import com.adolfo.characters.core.utils.CharactersConstants
import com.adolfo.characters.data.database.CharactersDatabase
import com.adolfo.characters.data.local.CharactersLocal
import com.adolfo.characters.data.local.CharactersLocalImp
import com.adolfo.characters.data.service.CharactersApi
import com.adolfo.characters.data.service.CharactersService
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.repository.CharactersRepositoryImp
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.characters.domain.usecases.GetCharacters
import org.koin.dsl.module

private val charactersDatabase = module {
    factory {
        Room.databaseBuilder(
            get(),
            CharactersDatabase::class.java,
            CharactersConstants.Database.DATABASE_NAME
        ).build()
    }

    factory<CharactersLocal> { CharactersLocalImp(get()) }
}

private val charactersRepository = module {
    factory<CharactersRepository> { CharactersRepositoryImp(get(), get(), get()) }
}

private val charactersSources = module {
    factory<CharactersApi> { CharactersService(get()) }
    factory<CharactersLocal> { CharactersLocalImp(get()) }
}

private val charactersUseCases = module {
    factory { GetCharacters(get()) }
    factory { GetCharacterDetail(get()) }
}

val characters = listOf(
    charactersDatabase,
    charactersRepository,
    charactersSources,
    charactersUseCases
)
