package andyradionov.github.io.hwallpaper.wallpaper

import andyradionov.github.io.hwallpaper.model.dto.Image

/**
 * @author Andrey Radionov
 */
interface WallpaperContract {

    interface Presenter {
        fun searchImages(query: String)
        fun getLatestImages()
        fun attachView(view: WallpaperContract.View)
        fun detachView()
    }

    interface View {
        fun showImages(images: List<Image>)
        fun showError()
        fun noInternet()
    }
}