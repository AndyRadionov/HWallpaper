package andyradionov.github.io.hwallpaper.model.network

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Andrey Radionov
 */

class ImagesStore(private val imagesApi: ImagesApi) {

    private var mSubscription: Disposable? = null

    /**
     * Searching images
     *
     * @param query User search query input or default gallery
     * @param page Page that needs to be load
     */
    fun searchImages(query: String, callback: ImagesCallback) {

        unsubscribe()

        mSubscription = imagesApi
                .searchImages(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { callback.onErrorLoading() }
                .map { it.results }
                .subscribe(
                        {
                            if (it.isEmpty()) {
                                callback.onErrorLoading()
                            } else {
                                callback.onSuccessLoading(it)
                            }
                        },
                        { callback.onErrorLoading() })
    }

    fun unsubscribe() {
        if (mSubscription?.isDisposed == true) {
            mSubscription?.dispose();
            mSubscription = null;
        }
    }
}
