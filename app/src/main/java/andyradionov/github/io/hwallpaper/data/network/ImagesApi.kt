package andyradionov.github.io.hwallpaper.data.network


import andyradionov.github.io.hwallpaper.app.CATEGORY_URL
import andyradionov.github.io.hwallpaper.app.SEARCH_URL
import andyradionov.github.io.hwallpaper.data.entities.Image
import andyradionov.github.io.hwallpaper.data.entities.SearchResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Api class
 *
 * @author Andrey Radionov
 */

interface ImagesApi {

    @GET(SEARCH_URL)
    fun searchImages(@Query("query") query: String): Observable<SearchResult>

    @GET(CATEGORY_URL)
    fun getLatestImages(): Observable<List<Image>>
}
