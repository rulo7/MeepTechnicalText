package com.racobos.meeptechnicaltest.data.datasources

import com.racobos.meeptechnicaltest.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DAOs {
    val retrofitDAO: ApiRetrofitService by lazy {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .addHeader("accept", "application/json")
                        .build()
                )
            }.build()

        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL_MEEP)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRetrofitService::class.java)
    }
}