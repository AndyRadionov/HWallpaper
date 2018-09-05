package andyradionov.github.io.hwallpaper.app

import android.app.Application
import android.content.Context
import andyradionov.github.io.hwallpaper.di.AppModule
import toothpick.Toothpick


/**
 * @author Andrey Radionov
 */
class App : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        val appScope = Toothpick.openScope(this)
        appScope.installModules(AppModule())
        appContext = this
    }
}
