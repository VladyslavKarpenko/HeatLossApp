package com.chi.heat.loss.app

import android.app.Application
import com.chi.heat.loss.app.di.dataModule
import com.chi.heat.loss.app.di.domainModule
import com.chi.heat.loss.app.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HeatLossApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@HeatLossApp)
            modules(
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}