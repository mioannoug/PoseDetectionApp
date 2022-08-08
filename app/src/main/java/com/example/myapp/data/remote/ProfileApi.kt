package com.example.myapp.data.remote

import com.example.myapp.data.BasicApiResponse
import com.example.myapp.data.response.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {

    @GET("/api/user/profile")
    suspend fun getProfile(
       @Query("userID") userId: String
    ): BasicApiResponse<ProfileResponse>


        companion object {
            const val BASE_URL = "http://10.0.2.2:8080/"
    }

}