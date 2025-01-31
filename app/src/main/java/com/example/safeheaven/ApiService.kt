package com.example.safeheaven

import LoginRequest
import LoginResponse
import SignupRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/signup")  // Ensure this matches your backend route
    fun signup(@Body signupRequest: SignupRequest): Call<String>

    @POST("api/auth/login")   // Ensure this matches your backend route
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}