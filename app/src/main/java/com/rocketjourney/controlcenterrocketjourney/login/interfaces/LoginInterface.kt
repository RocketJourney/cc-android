package com.rocketjourney.controlcenterrocketjourney.login.interfaces

import com.rocketjourney.controlcenterrocketjourney.login.requests.LoginRequest
import com.rocketjourney.controlcenterrocketjourney.login.requests.ResetPasswordRequest
import com.rocketjourney.controlcenterrocketjourney.login.requests.SignUpRequest
import com.rocketjourney.controlcenterrocketjourney.login.responses.LoginResponse
import com.rocketjourney.controlcenterrocketjourney.login.responses.SignUpResponse
import retrofit2.Call
import retrofit2.http.*

interface LoginInterface {

    @Headers("Content-Type: application/json", "Accept-Language: en")
    @POST("users")
    fun signUpRequest(request: SignUpRequest): Call<SignUpResponse>


    @Headers("Content-Type: application/json", "Accept-Language: en")
    @POST("sessions")
    fun loginRequest(request: LoginRequest): Call<LoginResponse>

    @Headers("Content-Type: application/json", "Accept-Language: en")
    @GET("invites/{userId}")
    fun validateEmail(@Path("userId") userId: String, @Query("email") email: String): Call<Void>

    @Headers("Content-Type: application/json", "Accept-Language: en")
    @GET("invites/{inviteId}")
    fun validateInvite(@Path("inviteId") inviteId: String): Call<Void>

    @Headers("Content-Type: application/json", "Accept-Language: en")
    @POST("sessions")
    fun resetPasswordRequest(request: ResetPasswordRequest): Call<Void>
}