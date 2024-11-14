package com.example.github_jialin.ui.activity.showActivity

import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github_jialin.R
import com.example.github_jialin.ui.adapter.RepoAdapter
import com.example.github_jialin.ui.base.BaseShowActivity
import com.example.github_jialin.utils.LogUtil
import com.example.github_jialin.utils.showToast

class RepositoriesActivity : BaseShowActivity() {

    companion object {
        const val TAG = "RepositoriesActivity"
    }

    private lateinit var content: String
    private lateinit var showRecyclerView: RecyclerView

    override fun getLayoutResId(): Int {
        return R.layout.activity_repositories
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        content = intent.getStringExtra("content")?:""
        setTitle("仓库")

        showRecyclerView = findViewById(R.id.showRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        val adapter = RepoAdapter(this, mutableListOf())
        showRecyclerView.layoutManager = layoutManager
        showRecyclerView.adapter = adapter

        // 监听数据变化，更新adapter
        viewModel.repoSearchLiveData.observe(this) { result->
            val list = result.getOrNull()
            if( list != null) {
                val repoList = list.items
                LogUtil.d(TAG, repoList.size.toString())
                val repoInfoList = repoList.map { it.toRepoInfo() }.toMutableList()
                loadingProgressBar.visibility = ProgressBar.GONE
                adapter.setRepoList(repoInfoList)
            } else {
                "仓库信息获取失败".showToast(this)
                result.exceptionOrNull()?.printStackTrace()
            }
        }

        viewModel.refresh(content,3)
    }

}