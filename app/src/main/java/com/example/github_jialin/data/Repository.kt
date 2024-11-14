package com.example.github_jialin.data

import androidx.lifecycle.liveData
import com.example.github_jialin.data.dao.CacheDao
import com.example.github_jialin.data.dao.DatabaseHelper
import com.example.github_jialin.data.model.RepoInfo
import com.example.github_jialin.data.network.GithubNetWork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {

    fun getUser(id: String) = fire(Dispatchers.IO) {
        val userResponse = GithubNetWork.getUser(id)
        if (userResponse.id != null) {
            Result.success(userResponse)
        } else {
            Result.failure(RuntimeException("response fail"))
        }
    }

    fun getRepos(id: String) = fire(Dispatchers.IO) {
        val reposResponse = GithubNetWork.getRepos(id)
        // 这里不太对，如果仓库列表为空，应该返回空列表
        if (reposResponse.isNotEmpty()) {
            Result.success(reposResponse)
        } else {
            Result.failure(RuntimeException("response fail"))
        }
    }

    fun getStars(id: String) = fire(Dispatchers.IO) {
        val starResponse = GithubNetWork.getStars(id)
        // 同上
        if (starResponse.isNotEmpty()) {
            Result.success(starResponse)
        } else {
            Result.failure(RuntimeException("response fail"))
        }
    }

    fun getSearchRepos(query: String) = fire(Dispatchers.IO) {
        val searchReposResponse = GithubNetWork.searchRepos(query)
        if (searchReposResponse.total_count!= 0) {
            Result.success(searchReposResponse)
        } else {
            Result.failure(RuntimeException("response fail"))
        }
    }

    fun getSearchUsers(query: String) = fire(Dispatchers.IO) {
        val searchUsersResponse = GithubNetWork.searchUsers(query)
        if (searchUsersResponse.total_count!= 0) {
            Result.success(searchUsersResponse)
        } else {
            Result.failure(RuntimeException("response fail"))
        }
    }

    fun getPopularRepos() = fire(Dispatchers.IO) {
        val popularReposResponse = GithubNetWork.searchPopularRepos()
        if (popularReposResponse.total_count!= 0) {
            Result.success(popularReposResponse)
        } else {
            Result.failure(RuntimeException("response fail"))
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }


    // 登录信息相关
    fun getAutoLoginStatus () = CacheDao.getAutoLoginStatus()

    fun getRememberPasswordStatus() = CacheDao.getRememberPasswordStatus()

    fun getAccount() = CacheDao.getAccount()

    fun getPassword() = CacheDao.getPassword()

    fun saveLoginInfo(account: String, password: String, autoLogin: Boolean, rememberPassword: Boolean) =
        CacheDao.saveLoginInfo(account, password, autoLogin, rememberPassword)

    // 搜索记录相关
    fun getSearchRecords(user: String) = DatabaseHelper.getSearchRecords(user)

    fun saveSearchRecords(user: String, query: String) = DatabaseHelper.saveSearchRecord(user, query)

    fun deleteSearchRecords(user: String) = DatabaseHelper.deleteSearchRecord(user)

    // 收藏相关DatabaseHelper
    fun insertCollectRepo(repoInfo: RepoInfo) = DatabaseHelper.insertRepo(repoInfo)

    fun getCollectReposByUser(user: String) = DatabaseHelper.getReposByUser(user)

    fun deleteCollectRepo(user: String) = DatabaseHelper.deleteRepo(user)

    fun isCollectRepoExist(repo: RepoInfo): Boolean = DatabaseHelper.isRepoExist(repo)

}