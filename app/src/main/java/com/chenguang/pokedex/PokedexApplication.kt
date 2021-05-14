package com.chenguang.pokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PokedexApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Initialize timber debug tree for logging
            Timber.plant(Timber.DebugTree())
        }
    }
}
