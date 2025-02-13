package com.example.plantapp.data

//import TrefleResponse
import com.example.plantapp.model.TrefleResponse
//import com.example.plantapp.model.TrefleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrefleApiService {
    @GET("api/v1/species/{id}")
    suspend fun getPlantDetails(
        @Path("id") id: Int,
        @Query("token") token: String
    ): Response<TrefleResponse>

    @GET("api/v1/species/search")
    suspend fun searchPlants(
        @Query("q") query: String,
        @Query("limit") limit: Int = 1,
        @Query("token") token: String
    ): Response<TrefleResponse>

    @GET("api/v1/plants")
    suspend fun getPlantsByFlowerColorAndSubstr(
        @Query("token") token: String,
        @Query("filter[flower_color]") flowerColor: String,
        @Query("q") substr: String  // Assuming 'q' can be used to search within 'scientific_name'
    ): Response<TrefleResponse>

}
