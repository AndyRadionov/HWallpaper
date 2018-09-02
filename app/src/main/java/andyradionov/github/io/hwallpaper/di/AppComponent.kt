package andyradionov.github.io.hwallpaper.di

import andyradionov.github.io.hwallpaper.wallpaper.WallpaperActivity
import dagger.Component
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Singleton
@Component(modules = [ImagesModule::class])
interface AppComponent {

    fun inject(activity: WallpaperActivity)
}