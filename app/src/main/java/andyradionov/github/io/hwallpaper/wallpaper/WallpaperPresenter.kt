package andyradionov.github.io.hwallpaper.wallpaper

import andyradionov.github.io.hwallpaper.app.App
import andyradionov.github.io.hwallpaper.model.dto.Image
import andyradionov.github.io.hwallpaper.model.network.ImagesCallback
import andyradionov.github.io.hwallpaper.model.network.ImagesStore
import andyradionov.github.io.hwallpaper.util.isInternetAvailable

/**
 * @author Andrey Radionov
 */
class WallpaperPresenter(private val imagesStore: ImagesStore) : WallpaperContract.Presenter, ImagesCallback {

    private var view: WallpaperContract.View? = null

    override fun searchImages(query: String) {
        if (isInternetAvailable(App.appContext)) {
            imagesStore.searchImages(query, this)
        } else {
            view?.noInternet()
        }
    }

    override fun getLatestImages() {
        if (isInternetAvailable(App.appContext)) {
            imagesStore.getImagesFromCategory(this)
        } else {
            view?.noInternet()
        }
    }

    override fun attachView(view: WallpaperContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onErrorLoading() {
        view?.showError()
    }

    override fun onSuccessLoading(images: List<Image>) {
        view?.showImages(images)
    }
}