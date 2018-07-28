package andyradionov.github.io.hwallpaper.app

import android.app.Application
import android.content.Context
import andyradionov.github.io.hwallpaper.app.di.AppComponent
import andyradionov.github.io.hwallpaper.app.di.DaggerAppComponent

/**
 * @author Andrey Radionov
 */
class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        appContext = this
    }
}