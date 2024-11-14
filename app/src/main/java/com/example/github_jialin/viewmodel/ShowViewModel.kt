package com.example.github_jialin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.github_jialin.data.Repository

class ShowViewModel : ViewModel() {
    companion object {
        const val USER: Int = 0
        const val REPO: Int = 1
        const val STAR: Int = 2
        const val SEARCH_REPO: Int = 3
        const val SEARCH_USER: Int = 4
    }

    private val getUserLiveData = MutableLiveData<String>()
    private val getReposLiveData = MutableLiveData<String>()
    private val getStarsLiveData = MutableLiveData<String>()
    private val getSearchRepoLiveData = MutableLiveData<String>()
    private val getSearchUserLiveData = MutableLiveData<String>()


    val userLiveData = getUserLiveData.switchMap { id ->
        Repository.getUser(id)
    }

    val reposLiveData = getReposLiveData.switchMap { id ->
        Repository.getRepos(id)
    }

    val starsLiveData = getStarsLiveData.switchMap { id ->
        Repository.getStars(id)
    }

    val repoSearchLiveData = getSearchRepoLiveData.switchMap { query ->
        Repository.getSearchRepos(query)
    }

    val userSearchLiveData = getSearchUserLiveData.switchMap { query ->
        Repository.getSearchUsers(query)
    }


    fun refresh(content: String, type: Int) {
       when (type){
           USER-> getUserLiveData.value = content
           REPO-> getReposLiveData.value = content
           STAR-> getStarsLiveData.value = content
           SEARCH_REPO-> getSearchRepoLiveData.value = content
           SEARCH_USER-> getSearchUserLiveData.value = content
       }
    }

}