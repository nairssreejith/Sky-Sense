package com.sreejithsnair.skysense.repository.weather

import com.sreejithsnair.skysense.data.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/current.json")
    suspend fun getWeatherData(
        @Query(value = "key") apiKey: String,
        @Query(value = "q") city: String
    ): Response<WeatherModel>

}