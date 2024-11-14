package com.example.github_jialin.data.network.netServer

import com.example.github_jialin.ClientApplication
import com.example.github_jialin.data.model.SearchReposResponse
import com.example.github_jialin.data.model.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchService {
    @Headers("Authorization: Bearer ${ClientApplication.TOKEN}")

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
    ): Call<SearchReposResponse>

    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
    ): Call<SearchUserResponse>

    @GET("search/repositories?q=stars:%3E1&sort=stars&order=desc")
    fun searchPopularRepositories(): Call<SearchReposResponse>
}