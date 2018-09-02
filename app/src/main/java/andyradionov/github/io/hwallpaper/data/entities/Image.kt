package andyradionov.github.io.hwallpaper.data.entities

/**
 * Model class contains Image id, background color and different image size urls
 *
 * @author Andrey Radionov
 */

data class Image(val id: String, val urls: Urls) {

    val fullImage: String
        get() = urls.full

    val mediumImage: String
        get() = urls.regular

    data class Urls(val regular: String, val full: String)
}
