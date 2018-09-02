package andyradionov.github.io.hwallpaper.data.network

import andyradionov.github.io.hwallpaper.data.entities.Image
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Andrey Radionov
 */

class ImagesRepository(private val imagesApi: ImagesApi) {

    fun searchImages(query: String): Observable<List<Image>> {

        return imagesApi.searchImages(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.results }
    }

    fun getImagesFromCategory(): Observable<List<Image>> {

        return imagesApi.getLatestImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
