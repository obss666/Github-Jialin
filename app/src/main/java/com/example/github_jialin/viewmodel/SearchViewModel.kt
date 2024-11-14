package com.example.github_jialin.viewmodel

import androidx.lifecycle.ViewModel
import com.example.github_jialin.data.Repository
import com.example.github_jialin.data.model.InfoListItem

class SearchViewModel: ViewModel()  {

    var searchRecordList = mutableListOf<String>()

    fun getSearchRecords(user: String) = Repository.getSearchRecords(user)

    fun saveSearchRecords(user: String, record: String) = Repository.saveSearchRecords(user, record)

    fun deleteSearchRecords(user: String) = Repository.deleteSearchRecords(user)
}