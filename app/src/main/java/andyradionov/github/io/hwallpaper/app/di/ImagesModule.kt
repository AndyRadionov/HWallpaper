package andyradionov.github.io.hwallpaper.app.di

import android.support.annotation.NonNull
import andyradionov.github.io.hwallpaper.app.API_KEY
import andyradionov.github.io.hwallpaper.app.BASE_URL
import andyradionov.github.io.hwallpaper.model.network.ImagesApi
import andyradionov.github.io.hwallpaper.model.network.ImagesStore
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

/**
 * @author Andrey Radionov
 */

@Module
class ImagesModule {

    @NonNull
    @Provides
    @Singleton
    fun provideWallpaperPresenter(imagesStore: ImagesStore): WallpaperContract.Presenter
            = WallpaperPresenter(imagesStore)

    @NonNull
    @Provides
    @Singleton
    fun provideImagesStore(imagesApi: ImagesApi) = ImagesStore(imagesApi)

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
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()

                    val request = original.newBuilder()
                            .header("Accept-Version", "v1")
                            .header("Authorization", API_KEY)
                            .method(original.method(), original.body())
                            .build()

                    chain.proceed(request)
                }.build()
    }
}