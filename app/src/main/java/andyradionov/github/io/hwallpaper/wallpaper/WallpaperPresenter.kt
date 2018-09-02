package andyradionov.github.io.hwallpaper.wallpaper

import andyradionov.github.io.hwallpaper.app.App
import andyradionov.github.io.hwallpaper.data.network.ImagesRepository
import andyradionov.github.io.hwallpaper.util.isInternetAvailable
import io.reactivex.disposables.Disposable

/**
 * @author Andrey Radionov
 */
class WallpaperPresenter(private val imagesRepository: ImagesRepository) : WallpaperContract.Presenter {

    private var view: WallpaperContract.View? = null
    private var mSubscription: Disposable? = null

    override fun searchImages(query: String) {
        if (isInternetAvailable(App.appContext)) {
            unsubscribe()

            mSubscription = imagesRepository.searchImages(query)
                    .doOnError { view?.showError() }
                    .subscribe(
                            {
                                if (it.isEmpty()) {
                                    view?.showError()
                                } else {
                                    view?.showImages(it)
                                }
                            },
                            { view?.showError() })
        } else {
            view?.noInternet()
        }
    }

    override fun getLatestImages() {
        if (isInternetAvailable(App.appContext)) {
            unsubscribe()

            imagesRepository.getImagesFromCategory()
                    .doOnError { view?.showError() }
                    .subscribe(
                            {
                                if (it.isEmpty()) {
                                    view?.showError()
                                } else {
                                    view?.showImages(it)
                                }
                            },
                            { view?.showError() })
        } else {
            view?.noInternet()
        }
    }

    override fun attachView(view: WallpaperContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
        unsubscribe()
    }

    fun unsubscribe() {
        if (mSubscription?.isDisposed == true) {
            mSubscription?.dispose();
            mSubscription = null;
        }
    }
}
