package andyradionov.github.io.hwallpaper.di

import android.support.annotation.NonNull
import android.util.Log
import andyradionov.github.io.hwallpaper.BuildConfig
import andyradionov.github.io.hwallpaper.app.BASE_URL
import andyradionov.github.io.hwallpaper.data.network.ImagesApi
import andyradionov.github.io.hwallpaper.data.network.ImagesRepository
import andyradionov.github.io.hwallpaper.wallpaper.WallpaperContract
import andyradionov.github.io.hwallpaper.wallpaper.WallpaperPresenter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import andyradionov.github.io.hwallpaper.app.TLSSocketFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException


/**
 * @author Andrey Radionov
 */

@Module
class ImagesModule {

    companion object {
        const val TAG = "ImagesModule"
    }

    @NonNull
    @Provides
    @Singleton
    fun provideWallpaperPresenter(imagesRepository: ImagesRepository): WallpaperContract.Presenter
            = WallpaperPresenter(imagesRepository)

    @NonNull
    @Provides
    @Singleton
    fun provideImagesStore(imagesApi: ImagesApi) = ImagesRepository(imagesApi)

    @NonNull
    @Provides
    @Singleton
    fun provideImagesApi(httpClient: OkHttpClient): ImagesApi {

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(ImagesApi::class.java)
    }

    @NonNull
    @Provides
    @Singleton
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
    @Provides
    @Singleton
    fun provideOkHttp(clientBuilder: OkHttpClient.Builder): OkHttpClient {
        val apiKey = "Client-ID ${BuildConfig.ApiKey}"

        return clientBuilder
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