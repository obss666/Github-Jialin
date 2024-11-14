package com.example.github_jialin.ui.activity.showActivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github_jialin.ClientApplication
import com.example.github_jialin.R
import com.example.github_jialin.ui.adapter.SelectedAdapter
import com.example.github_jialin.ui.adapter.UnselectedAdapter
import com.example.github_jialin.ui.base.BaseShowActivity
import com.example.github_jialin.utils.LogUtil
import com.example.github_jialin.utils.ToucheCallBack
import com.example.github_jialin.utils.showToast
import com.example.github_jialin.viewmodel.CollectionViewModel


class CollectionActivity : BaseShowActivity() {

    companion object {
        const val TAG = "CollectionActivity111111111111"
    }

    private val mViewModel by lazy { ViewModelProvider(this).get(CollectionViewModel::class.java) }

    private lateinit var selectedRecyclerView: RecyclerView
    private lateinit var unselectedRecyclerView: RecyclerView

    private lateinit var unselectedAdapter: UnselectedAdapter
    private lateinit var selectedAdapter: SelectedAdapter

    private lateinit var saveButton: Button

    override fun getLayoutResId(): Int {
        return R.layout.activity_collection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("收藏夹")

        selectedRecyclerView = findViewById(R.id.selectedRecyclerView)
        unselectedRecyclerView = findViewById(R.id.unselectedRecyclerView)
        selectedRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        unselectedRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER

        unselectedAdapter = UnselectedAdapter(this, mutableListOf())
        selectedAdapter = SelectedAdapter(this, mutableListOf())

        selectedRecyclerView.layoutManager = LinearLayoutManager(this)
        selectedRecyclerView.adapter = selectedAdapter
        unselectedRecyclerView.layoutManager = LinearLayoutManager(this)
        unselectedRecyclerView.adapter = unselectedAdapter
        saveButton = findViewById(R.id.saveButton)

        val touchHelper = ItemTouchHelper(ToucheCallBack(selectedAdapter));
        touchHelper.attachToRecyclerView(selectedRecyclerView)

        // 快速点击容易出现重复点击，加个防抖
        var lastClickTime = 0L
        unselectedAdapter.setOnItemClickListener { position ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 500) {
                lastClickTime = currentTime
                val repoInfo = unselectedAdapter.getRepoInfo(position)
                unselectedAdapter.removeRepo(position)
                selectedAdapter.addRepo(repoInfo)
            }
        }

        selectedAdapter.setOnItemClickListener { position ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 500) {
                lastClickTime = currentTime
                val repoInfo = selectedAdapter.getRepoInfo(position)
                selectedAdapter.removeRepo(position)
                unselectedAdapter.addRepo(repoInfo)
            }
        }

        viewModel.reposLiveData.observe(this) { result->
            val list = result.getOrNull()
            if( list != null) {
                var repoInfoList = list.map { it.toRepoInfo() }.toMutableList()
                repoInfoList = repoInfoList.filter {!mViewModel.isCollectRepoExist(it) }.toMutableList()
                loadingProgressBar.visibility = ProgressBar.GONE
                unselectedAdapter.setRepoList(repoInfoList)
            } else {
                "仓库信息获取失败".showToast(this)
                result.exceptionOrNull()?.printStackTrace()
            }
        }

        saveButton.setOnClickListener {
            mViewModel.deleteCollectRepo(ClientApplication.USERNAME)

            val repoList = selectedAdapter.getRepoList()

            for (repo in repoList) {
                LogUtil.d(TAG, repo.login!!)
                LogUtil.d(TAG, repo.name!!)
            }

            for (repoInfo in selectedAdapter.getRepoList()) {
                mViewModel.insertCollectRepo(repoInfo)
            }

            mViewModel.getCollectReposByUser(ClientApplication.USERNAME)
            finish()
        }

        mViewModel.reposListLiveData.observe(this) {
            val repoList = it.toList()
            selectedAdapter.setRepoList(repoList)
        }

        mViewModel.getCollectReposByUser(ClientApplication.USERNAME)
        viewModel.refresh(ClientApplication.USERNAME,1)

    }
}