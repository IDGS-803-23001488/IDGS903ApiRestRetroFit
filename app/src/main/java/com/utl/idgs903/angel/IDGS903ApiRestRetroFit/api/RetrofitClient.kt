package com.utl.idgs903.angel.IDGS903ApiRestRetroFit.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.utl.idgs903.angel.IDGS903ApiRestRetroFit.BuildConfig

object RetrofitClient {
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
