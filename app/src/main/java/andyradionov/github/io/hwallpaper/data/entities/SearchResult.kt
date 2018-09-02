package andyradionov.github.io.hwallpaper.data.entities

import com.google.gson.annotations.SerializedName

/**
 * DTO class for search result. It wraps List of [Image] and total pages number
 *
 * @author Andrey Radionov
 */

data class SearchResult (val results: List<Image>,
                         @SerializedName("total_pages") val totalPages: Int)
