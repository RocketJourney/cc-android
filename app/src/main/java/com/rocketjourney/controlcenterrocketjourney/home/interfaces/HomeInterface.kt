package com.rocketjourney.controlcenterrocketjourney.home.interfaces

import com.rocketjourney.controlcenterrocketjourney.home.requests.InviteRequest
import com.rocketjourney.controlcenterrocketjourney.home.responses.ClubDataResponse
import com.rocketjourney.controlcenterrocketjourney.home.responses.InviteResponse
import com.rocketjourney.controlcenterrocketjourney.home.responses.SpotStatusResponse
import com.rocketjourney.controlcenterrocketjourney.home.responses.SpotUsersResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeInterface {

    @Headers("Content-Type: application/json", "Accept-Language: en")
    @GET("clubs/{clubId}/spots")
    fun getClubSpots(@Header("Authorization") token: String, @Path("clubId") clubId: Int): Call<ClubDataResponse>


    @Headers("Content-Type: application/json", "Accept-Language: en")
    @GET("clubs/{clubId}/spots/{spotId}/status")
    fun getSpotStatus(@Header("Authorization") token: String, @Path("clubId") clubId: Int,
                      @Path("spotId") spotIdOrAllSpots: String): Call<SpotStatusResponse>


    @Headers("Content-Type: application/json", "Accept-Language: en")
    @GET("clubs/{clubId}/spots/{spotId}/users")
    fun getSpotUsers(@Header("Authorization") token: String, @Path("clubId") clubId: Int?,
                      @Path("spotId") spotIdOrAllSpots: String?, @Query("page") page: Int): Call<SpotUsersResponse>

    @Headers("Content-Type: application/json", "Accept-Language: en")
    @POST("invites/")
    fun createInviteRequest(@Header("Authorization") token: String, @Body request: InviteRequest): Call<InviteResponse>
}