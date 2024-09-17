package library.core.dagger

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import library.core.BuildConfig
import library.core.dagger.qualifiers.Qualifiers
import library.core.network.api.WeatherApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class ApiModule(private val application: Application) {

    companion object {
        private const val TIMEOUT = 30L
    }

    @Provides
    @Singleton
    fun getCoroutineScope(): CoroutineScope {
        val coroutineContext: CoroutineContext = Job() + Dispatchers.IO
        return CoroutineScope(coroutineContext)
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    internal fun provideCache(application: Application): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong()
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    @Provides
    @Singleton
    @Qualifiers.ApiClient
    internal fun provideOkHttpClient(
        cache: Cache
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(logging)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Qualifiers.ApiClient
    @Provides
    internal fun provideRetrofit(gson: Gson, @Qualifiers.ApiClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    internal fun getMobileService(@Qualifiers.ApiClient retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)
}