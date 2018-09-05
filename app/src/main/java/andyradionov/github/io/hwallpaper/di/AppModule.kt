package andyradionov.github.io.hwallpaper.di

import android.support.annotation.NonNull
import android.util.Log
import andyradionov.github.io.hwallpaper.BuildConfig
import andyradionov.github.io.hwallpaper.app.BASE_URL
import andyradionov.github.io.hwallpaper.app.TLSSocketFactory
import andyradionov.github.io.hwallpaper.data.network.ImagesApi
import andyradionov.github.io.hwallpaper.data.network.ImagesRepository
import andyradionov.github.io.hwallpaper.wallpaper.WallpaperContract
import andyradionov.github.io.hwallpaper.wallpaper.WallpaperPresenter
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.config.Module


/**
 * @author Andrey Radionov
 */

class AppModule : Module() {

    init {
        bind(WallpaperContract.Presenter::class.java).toInstance(provideWallpaperPresenter())
        bind(OkHttpClient.Builder::class.java).toInstance(provideOkHttpSSLBuilder())
    }

    companion object {
        const val TAG = "AppModule"
    }



    @NonNull
    fun provideWallpaperPresenter(): WallpaperContract.Presenter
            = WallpaperPresenter(provideImagesStore())



    @NonNull
    fun provideOkHttpSSLBuilder(): OkHttpClient.Builder {
        return try {
            OkHttpClient.Builder()
                    .sslSocketFactory(TLSSocketFactory())
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            OkHttpClient.Builder()
        }
    }

    @NonNull
    private fun provideImagesStore() = ImagesRepository(provideImagesApi())

    @NonNull
    private fun provideImagesApi(): ImagesApi {

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttp())
                .build()
                .create(ImagesApi::class.java)
    }

    @NonNull
    private fun provideOkHttp(): OkHttpClient {
        val apiKey = "Client-ID ${BuildConfig.ApiKey}"

        return provideOkHttpSSLBuilder()
                .addInterceptor { chain ->
                    val original = chain.request()

                    val request = original.newBuilder()
                            .header("Accept-Version", "v1")
                            .header("Authorization", apiKey)
                            .method(original.method(), original.body())
                            .build()

                    chain.proceed(request)
                }.build()
    }
}