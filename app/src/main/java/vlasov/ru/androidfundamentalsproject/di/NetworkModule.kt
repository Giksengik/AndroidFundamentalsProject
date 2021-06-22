package vlasov.ru.androidfundamentalsproject.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import vlasov.ru.androidfundamentalsproject.network.json.JsonMovieAPI
import java.util.concurrent.TimeUnit

class NetworkModule {

private val baseUrl = "https://api.themoviedb.org/"
private val version = "3/"

private val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
    coerceInputValues = true
}

private val contentType = "application/json".toMediaType()

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addNetworkInterceptor(loggingInterceptor)
        .addInterceptor(ApiKeyInterceptor())
        .build()

private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl + version)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(httpClient)

private val retrofit = retrofitBuilder.build()

val api: JsonMovieAPI by lazy { retrofit.create(JsonMovieAPI::class.java) }

}

class ApiKeyInterceptor: Interceptor {

    companion object {
        private const val API_KEY = "d45b41aea7d83432345ca1cd4d89267b"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val urlBuilder = origin.url.newBuilder()
        val url = urlBuilder
                .addQueryParameter("api_key", API_KEY)
                .build()

        val requestBuilder = origin.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}


