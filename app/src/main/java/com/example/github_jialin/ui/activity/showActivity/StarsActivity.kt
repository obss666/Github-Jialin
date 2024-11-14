package com.example.github_jialin.ui.activity.showActivity

import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github_jialin.ClientApplication
import com.example.github_jialin.R
import com.example.github_jialin.ui.adapter.RepoAdapter
import com.example.github_jialin.ui.base.BaseShowActivity
import com.example.github_jialin.utils.LogUtil
import com.example.github_jialin.utils.showToast

class StarsActivity : BaseShowActivity() {

    companion object {
        const val TAG = "YourStarsActivity"
    }

    private lateinit var showRecyclerView: RecyclerView

    override fun getLayoutResId(): Int {
        return R.layout.activity_stars
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle("星标")
        showRecyclerView = findViewById(R.id.showRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        val adapter = RepoAdapter(this, mutableListOf(), R.layout.item_repo)
        showRecyclerView.layoutManager = layoutManager
        showRecyclerView.adapter = adapter

        viewModel.starsLiveData.observe(this) { result->
            val list = result.getOrNull()
            if( list != null) {
                LogUtil.d(TAG, list.size.toString())
                val repoInfoList = list.map { it.toRepoInfo() }.toMutableList()
                loadingProgressBar.visibility = ProgressBar.GONE
                adapter.setRepoList(repoInfoList)
            } else {
                "仓库信息获取失败".showToast(this)
                result.exceptionOrNull()?.printStackTrace()
            }
        }
        viewModel.refresh(ClientApplication.USERNAME, 2)
    }
}
