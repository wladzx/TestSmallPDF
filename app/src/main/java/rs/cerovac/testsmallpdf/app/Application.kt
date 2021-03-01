package rs.cerovac.testsmallpdf.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import rs.cerovac.testsmallpdf.BuildConfig
import rs.cerovac.testsmallpdf.app.modules.*
import timber.log.Timber

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(allModules)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}