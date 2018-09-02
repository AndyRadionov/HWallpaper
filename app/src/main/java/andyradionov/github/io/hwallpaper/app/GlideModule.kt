package andyradionov.github.io.hwallpaper.app

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

/**
 * @author Andrey Radionov
 */
@GlideModule
class GlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val okHttpBuilder = App.appComponent.getOkHttpBuilder()
        val factory = OkHttpUrlLoader.Factory(okHttpBuilder.build())

        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }
}
