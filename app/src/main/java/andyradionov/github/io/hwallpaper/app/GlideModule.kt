package andyradionov.github.io.hwallpaper.app

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import toothpick.Toothpick
import java.io.InputStream
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@GlideModule
class GlideModule : AppGlideModule() {

    @Inject
    lateinit var okHttpBuilder: OkHttpClient.Builder

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val scope = Toothpick.openScope(App.appContext)
        Toothpick.inject(this, scope)
        val factory = OkHttpUrlLoader.Factory(okHttpBuilder.build())

        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
    }
}
