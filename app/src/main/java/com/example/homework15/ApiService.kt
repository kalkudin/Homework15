package com.example.homework15

import retrofit2.http.GET

interface ApiService {
    @GET("v3/744fa574-a483-43f6-a1d7-c65c87b5d9b2")
    suspend fun getConversations() : List<UserInformation>
}