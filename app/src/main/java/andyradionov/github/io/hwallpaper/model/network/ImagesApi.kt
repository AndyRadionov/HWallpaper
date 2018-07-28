package andyradionov.github.io.hwallpaper.model.network


import andyradionov.github.io.hwallpaper.app.CATEGORY_URL
import andyradionov.github.io.hwallpaper.app.SEARCH_URL
import andyradionov.github.io.hwallpaper.model.dto.Image
import andyradionov.github.io.hwallpaper.model.dto.SearchResultDto
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
    fun searchImages(@Query("query") query: String): Observable<SearchResultDto>

    @GET(CATEGORY_URL)
    fun getLatestImages(): Observable<List<Image>>
}
