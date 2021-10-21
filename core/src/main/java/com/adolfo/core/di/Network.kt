package com.adolfo.core.di

import com.adolfo.core.network.NetworkConfig
import com.adolfo.core.network.NetworkTools
import org.koin.dsl.module

val networkModule = module {
    single { NetworkConfig().getRetrofit() }

    factory { NetworkTools(get()) }
}
