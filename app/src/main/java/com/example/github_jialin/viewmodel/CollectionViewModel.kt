package com.example.github_jialin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_jialin.data.Repository
import com.example.github_jialin.data.model.RepoInfo
import kotlinx.coroutines.launch

class CollectionViewModel :ViewModel()  {

    private val _reposListLiveData = MutableLiveData<List<RepoInfo>>()
    val reposListLiveData: LiveData<List<RepoInfo>> = _reposListLiveData

    fun insertCollectRepo(repoInfo: RepoInfo) = Repository.insertCollectRepo(repoInfo)

    fun getCollectReposByUser(user: String) {
        viewModelScope.launch {
            val repos = Repository.getCollectReposByUser(user)
            _reposListLiveData.value = repos
        }
    }

    fun deleteCollectRepo(user: String) = Repository.deleteCollectRepo(user)

    fun isCollectRepoExist(repo: RepoInfo) = Repository.isCollectRepoExist(repo)
}