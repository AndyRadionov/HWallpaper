package andyradionov.github.io.hwallpaper.model.network


import andyradionov.github.io.hwallpaper.app.PAGE_SIZE
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

    @GET("search/photos?per_page=" + PAGE_SIZE)
    fun searchImages(@Query("query") query: String): Observable<SearchResultDto>
}
