package com.adolfo.core_testing

import com.adolfo.core.network.NetworkTools
import org.koin.dsl.module

val testingModule = module {
    single { NetworkTools(get()) }
}
