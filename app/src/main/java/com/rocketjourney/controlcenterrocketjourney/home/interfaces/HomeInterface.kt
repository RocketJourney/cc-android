package com.rocketjourney.controlcenterrocketjourney.home.interfaces

import com.rocketjourney.controlcenterrocketjourney.home.responses.ClubDataResponse
import com.rocketjourney.controlcenterrocketjourney.home.responses.SpotStatusResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface HomeInterface {

    @Headers("Content-Type: application/json", "Accept-Language: en")
    @GET("clubs/{clubId}/spots")
    fun getClubSpots(@Header("Authorization") token: String, @Path("clubId") clubId: Int): Call<ClubDataResponse>


    @Headers("Content-Type: application/json", "Accept-Language: en")
    @GET("clubs/{clubId}/spots/{spotId}/status")
    fun getSpotStatus(@Header("Authorization") token: String, @Path("clubId") clubId: Int,
                      @Path("spotId") spotIdOrAllSpots: String): Call<SpotStatusResponse>
}