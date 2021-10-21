package com.adolfo.marvel

import android.app.Application
import com.adolfo.characters.core.di.characters
import com.adolfo.core.di.networkModule
import com.adolfo.core.logging.HyperlinkDebugTree
import com.adolfo.core.utils.DebugUtils
import com.adolfo.marvel.common.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import timber.log.Timber

@Suppress("unused")
class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MarvelApplication)
            modules(loadListModules())
        }

        enableDebugConfig()
    }

    private fun enableDebugConfig() {
        if (DebugUtils.isDebug()) {
            Timber.plant(HyperlinkDebugTree())
        }
    }

    private fun loadListModules(): List<Module> {
        val coreModules = listOf(
            networkModule
        )

        val modules = mutableListOf<Module>()
        modules.addAll(coreModules)
        modules.add(viewModels)
        modules.addAll(characters)

        return modules.toList()
    }
}
