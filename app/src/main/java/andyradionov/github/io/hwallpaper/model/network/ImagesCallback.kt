package andyradionov.github.io.hwallpaper.model.network

import andyradionov.github.io.hwallpaper.model.dto.Image

/**
 * @author Andrey Radionov
 */

interface ImagesCallback {

    fun onErrorLoading()

    fun onSuccessLoading(images: List<Image>)
}
