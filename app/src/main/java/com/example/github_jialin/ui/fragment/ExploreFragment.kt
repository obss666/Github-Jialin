package com.example.github_jialin.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.github_jialin.ClientApplication
import com.example.github_jialin.R
import com.example.github_jialin.ui.activity.MainActivity
import com.example.github_jialin.ui.activity.showActivity.RepositoriesActivity
import com.example.github_jialin.ui.activity.showActivity.RepositoriesActivity.Companion
import com.example.github_jialin.ui.adapter.RepoAdapter
import com.example.github_jialin.utils.LogUtil
import com.example.github_jialin.utils.showToast
import com.example.github_jialin.viewmodel.ShowViewModel

class ExploreFragment : Fragment() {

    companion object {
        const val TAG = "ExploreFragment11111111111"
    }

    private val mViewModel: ShowViewModel by activityViewModels()
    private lateinit var shadowLine: View
    private lateinit var hotRecyclerView: RecyclerView
    private lateinit var exploreSwipeRefresh :SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shadowLine = view.findViewById(R.id.exploreShadowLine)
        hotRecyclerView = view.findViewById(R.id.hotRecyclerView)
        exploreSwipeRefresh = view.findViewById(R.id.exploreSwipeRefresh)
        hotRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER

        hotRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    shadowLine.visibility = View.VISIBLE
                } else {
                    shadowLine.visibility = View.GONE
                }
            }
        })

        exploreSwipeRefresh.setOnRefreshListener {
            refresh()
        }

        val adapter = RepoAdapter (requireActivity(), mutableListOf(), R.layout.item_explore)
        hotRecyclerView.layoutManager = LinearLayoutManager(activity)
        hotRecyclerView.adapter = adapter

        mViewModel.popularRepoLiveData.observe(viewLifecycleOwner) { result->
            val list = result.getOrNull()
            if( list!= null) {
                val repoList = list.items
                val repoInfoList = repoList.map { it.toRepoInfo() }.toMutableList()
                LogUtil.d(TAG, repoList.size.toString())
                exploreSwipeRefresh.isRefreshing = false
                adapter.setRepoList(repoInfoList)
            } else {
                "仓库信息获取失败".showToast(this.requireContext())
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        exploreSwipeRefresh.isRefreshing = true
        mViewModel.refresh("default_popular_repo_query",5)
    }
}