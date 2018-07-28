package andyradionov.github.io.hwallpaper.model.dto

/**
 * Model class contains Image id, background color and different image size urls
 *
 * @author Andrey Radionov
 */

data class Image(val id: String, val urls: Urls) {

    val smallImage: String
        get() = urls.small

    val mediumImage: String
        get() = urls.regular

    data class Urls(val small: String, val regular: String)
}
