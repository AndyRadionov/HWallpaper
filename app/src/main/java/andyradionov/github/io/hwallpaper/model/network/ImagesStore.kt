package andyradionov.github.io.hwallpaper.model.network

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Andrey Radionov
 */

class ImagesStore(private val imagesApi: ImagesApi) {

    private var mSubscription: Disposable? = null


    fun searchImages(query: String, callback: ImagesCallback) {

        unsubscribe()

        mSubscription = imagesApi.searchImages(query)
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

    fun getImagesFromCategory(callback: ImagesCallback) {

        unsubscribe()

        mSubscription = imagesApi.getLatestImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { callback.onErrorLoading() }
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
