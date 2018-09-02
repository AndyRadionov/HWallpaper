package andyradionov.github.io.hwallpaper.di

import andyradionov.github.io.hwallpaper.wallpaper.WallpaperActivity
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Singleton
@Component(modules = [ImagesModule::class])
interface AppComponent {

    fun getOkHttpBuilder(): OkHttpClient.Builder

    fun inject(activity: WallpaperActivity)
}