package com.adolfo.characters.core.di

import androidx.room.Room
import com.adolfo.characters.core.utils.CharactersConstants
import com.adolfo.characters.data.service.CharactersService
import com.adolfo.characters.data.database.CharactersDatabase
import com.adolfo.characters.data.datasource.CharactersDatasource
import com.adolfo.characters.data.datasource.CharactersDatasourceImp
import com.adolfo.characters.data.local.CharactersLocal
import com.adolfo.characters.data.local.CharactersLocalImp
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.repository.CharactersRepositoryImp
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.characters.domain.usecases.GetCharacters
import org.koin.dsl.module

val charactersRepository = module {
    factory<CharactersRepository> { CharactersRepositoryImp(get()) }
}

val charactersService = module {
    factory { CharactersService(get()) }
}

val charactersUseCases = module {
    factory { GetCharacters(get()) }
    factory { GetCharacterDetail(get()) }
}

val charactersDatabase = module {
    factory {
        Room.databaseBuilder(
            get(),
            CharactersDatabase::class.java,
            CharactersConstants.Database.DATABASE_NAME
        ).build()
    }

    factory<CharactersLocal> { CharactersLocalImp(get()) }
}

val charactersDatasource = module {
    factory<CharactersDatasource> { CharactersDatasourceImp(get(), get(), get()) }
}

val characters = listOf(
    charactersDatabase,
    charactersService,
    charactersRepository,
    charactersUseCases,
    charactersDatasource
)
