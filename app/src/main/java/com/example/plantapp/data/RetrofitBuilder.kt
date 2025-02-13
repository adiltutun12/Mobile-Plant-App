package com.example.plantapp

import com.example.plantapp.data.TrefleApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object RetrofitBuilder {
    private const val BASE_URL = "https://trefle.io/"

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(object : TypeToken<List<String>>() {}.type, SynonymsDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val trefleApiService: TrefleApiService = getRetrofit().create(TrefleApiService::class.java)
}
