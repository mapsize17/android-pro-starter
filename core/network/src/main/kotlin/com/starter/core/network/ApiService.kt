package com.starter.core.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): NetworkResponse<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): NetworkResponse<AuthResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshRequest): NetworkResponse<AuthResponse>

    @GET("user/profile")
    suspend fun getProfile(): NetworkResponse<UserProfileResponse>

    @GET("items")
    suspend fun getItems(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): NetworkResponse<PaginatedResponse<ItemResponse>>
}
