package com.adolfo.core.network

import com.adolfo.core.BuildConfig
import com.adolfo.core.extensions.md5
import com.adolfo.core.utils.DebugUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConfig {

    private lateinit var retrofitClient: Retrofit

    init {
        initRetrofit()
    }

    private fun getOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        with(client) {
            if (DebugUtils.isDebug()) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                this.addInterceptor(interceptor)
            }

            client.addInterceptor { interceptor ->
                val timestamp = System.currentTimeMillis().toString()
                val url = interceptor.request().url
                    .newBuilder()
                    .addQueryParameter("ts", timestamp)
                    .addQueryParameter("apikey", BuildConfig.MARVEL_PUBLIC_KEY)
                    .addQueryParameter(
                        "hash",
                        (timestamp + BuildConfig.MARVEL_PRIVATE_KEY +
                                BuildConfig.MARVEL_PUBLIC_KEY).md5()
                    )
                    .build()

                val newRequest = interceptor.request()
                    .newBuilder()
                    .url(url)
                    .build()

                interceptor.proceed(newRequest)
            }
        }

        return client.build()
    }

    fun initRetrofit(url: String = BuildConfig.ENDPOINT) {
        retrofitClient = Retrofit.Builder()
            .baseUrl(url)
            .client(getOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofit(): Retrofit {
        return retrofitClient
    }
}
