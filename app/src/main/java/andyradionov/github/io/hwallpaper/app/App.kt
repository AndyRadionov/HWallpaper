package andyradionov.github.io.hwallpaper.app

import android.app.Application
import android.content.Context
import andyradionov.github.io.hwallpaper.di.appModule
import andyradionov.github.io.hwallpaper.wallpaper.WallpaperContract
import andyradionov.github.io.hwallpaper.wallpaper.WallpaperPresenter
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module


/**
 * @author Andrey Radionov
 */
class App : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))
        appContext = this
    }
}
