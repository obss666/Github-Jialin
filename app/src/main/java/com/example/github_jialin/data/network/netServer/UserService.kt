package com.example.github_jialin.data.network.netServer

import com.example.github_jialin.ClientApplication
import com.example.github_jialin.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserService {
    @Headers("Authorization: Bearer ${ClientApplication.TOKEN}")
    @GET("users/{id}")

    fun getUser(@Path("id") id: String): Call<UserResponse>
}