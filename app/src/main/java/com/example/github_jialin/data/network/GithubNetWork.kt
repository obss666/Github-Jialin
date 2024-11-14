package com.example.github_jialin.data.network

import com.example.github_jialin.data.network.netServer.RepoService
import com.example.github_jialin.data.network.netServer.SearchService
import com.example.github_jialin.data.network.netServer.StarService
import com.example.github_jialin.data.network.netServer.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object GithubNetWork {
    private val userService = ServiceCreator.create<UserService>()
    suspend fun getUser(id: String) = userService.getUser(id).await()

    private val repoService = ServiceCreator.create<RepoService>()
    suspend fun getRepos(id: String) = repoService.getRepos(id).await()

    private val starService = ServiceCreator.create<StarService>()
    suspend fun getStars(id: String) = starService.getStars(id).await()

    private val searchService = ServiceCreator.create<SearchService>()
    suspend fun searchRepos(query: String) = searchService.searchRepositories(query).await()
    suspend fun searchUsers(query: String) = searchService.searchUsers(query).await()
    suspend fun searchPopularRepos() = searchService.searchPopularRepositories().await()


    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}