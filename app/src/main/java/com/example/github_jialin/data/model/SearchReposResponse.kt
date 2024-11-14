package com.example.github_jialin.data.model

data class SearchReposResponse(
    val incomplete_results: Boolean,
    val items: List<ReposResponseItem>,
    val total_count: Int
)
