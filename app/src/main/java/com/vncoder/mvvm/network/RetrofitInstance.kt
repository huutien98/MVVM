package com.vncoder.mvvm.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://private-amnesiac-8522d-autopilot.apiary-proxy.com/v1/"
    private val interceptor = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .apply {addInterceptor(MyInterceptor())
        }.build()

    val instance: RestApiService by lazy {
        val retrofit = Retrofit.Builder()
            .client(interceptor)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RestApiService::class.java)
    }
}