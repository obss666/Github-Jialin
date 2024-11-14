package com.example.github_jialin.data.network.netServer

import com.example.github_jialin.ClientApplication
import com.example.github_jialin.data.model.ReposResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RepoService {
    @Headers("Authorization: Bearer ${ClientApplication.TOKEN}")
    @GET("users/{id}/repos")

    fun getRepos(@Path("id") id: String): Call<ReposResponse>
}