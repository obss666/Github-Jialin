package com.example.github_jialin.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.github_jialin.ClientApplication
import com.example.github_jialin.R
import com.example.github_jialin.ui.activity.MainActivity
import com.example.github_jialin.ui.activity.showActivity.PullRequestsActivity
import com.example.github_jialin.ui.activity.showActivity.CollectionActivity
import com.example.github_jialin.ui.activity.showActivity.DiscussActivity
import com.example.github_jialin.ui.activity.showActivity.IssuesActivity
import com.example.github_jialin.ui.activity.showActivity.MyRepositoriesActivity
import com.example.github_jialin.ui.activity.showActivity.OrganizationActivity
import com.example.github_jialin.ui.adapter.EasyRepoAdapter
import com.example.github_jialin.utils.showToast
import com.example.github_jialin.viewmodel.CollectionViewModel

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    private lateinit var mHomeSwipeRefresh :SwipeRefreshLayout
    private lateinit var mHomeNestedScrollView: NestedScrollView
    private lateinit var issuesItem: LinearLayout
    private lateinit var requestItem: LinearLayout
    private lateinit var discussItem: LinearLayout
    private lateinit var repoItem: LinearLayout
    private lateinit var organizationItem: LinearLayout
    private lateinit var addCollectionCardView: CardView
    private lateinit var editButton: Button
    private lateinit var collectionRecyclerView: RecyclerView
    private lateinit var tipLayout: LinearLayout
    private lateinit var startQuickCardView: CardView


    private val mCollectionViewModel by lazy { ViewModelProvider(this).get(CollectionViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mHomeSwipeRefresh = view.findViewById(R.id.homeSwipeRefresh)
        mHomeNestedScrollView = view.findViewById(R.id.homeNestedScrollView)
        issuesItem = view.findViewById(R.id.issuesItem)
        requestItem = view.findViewById(R.id.requestItem)
        discussItem = view.findViewById(R.id.discussItem)
        repoItem = view.findViewById(R.id.repoItem)
        organizationItem = view.findViewById(R.id.organizationItem)
        addCollectionCardView = view.findViewById(R.id.addCollectionCardView)
        editButton = view.findViewById(R.id.editButton)
        collectionRecyclerView = view.findViewById(R.id.collectionRecyclerView)
        tipLayout = view.findViewById(R.id.tipLayout)
        startQuickCardView = view.findViewById(R.id.startQuickCardView)

        mHomeSwipeRefresh.setOnRefreshListener {
            refresh()
        }

        mHomeNestedScrollView.overScrollMode = View.OVER_SCROLL_IF_CONTENT_SCROLLS

        //我的工作
        issuesItem.setOnClickListener {
            startActivity(Intent(activity, IssuesActivity::class.java))
        }
        requestItem.setOnClickListener {
            startActivity(Intent(activity, PullRequestsActivity::class.java))
        }
        discussItem.setOnClickListener {
            startActivity(Intent(activity, DiscussActivity::class.java))
        }
        repoItem.setOnClickListener{
            startActivity(Intent(activity, MyRepositoriesActivity::class.java))
        }
        organizationItem.setOnClickListener {
            startActivity(Intent(activity, OrganizationActivity::class.java))
        }

        // 收藏夹
        editButton.setOnClickListener {
            startActivity(Intent(activity, CollectionActivity::class.java))
        }
        addCollectionCardView.setOnClickListener {
            startActivity(Intent(activity, CollectionActivity::class.java))
        }
        val adapter = EasyRepoAdapter (requireActivity(), mutableListOf())

        collectionRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        collectionRecyclerView.layoutManager = LinearLayoutManager(activity)
        collectionRecyclerView.adapter = adapter

        mCollectionViewModel.reposListLiveData.observe(viewLifecycleOwner) { result ->
            val repoList = result.toList()
            if (repoList.isNotEmpty()) {
                tipLayout.visibility = View.GONE
            } else {
                tipLayout.visibility = View.VISIBLE
            }
            adapter.setRepoList(repoList)
            mHomeSwipeRefresh.isRefreshing = false
        }

        // 快捷
        startQuickCardView.setOnClickListener {
            "开始使用".showToast(requireActivity())
        }
    }

    override fun onResume() {
        super.onResume()
        mHomeNestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = mHomeNestedScrollView.scrollY
            val activity = activity as? MainActivity
            if (scrollY > 0) {
                activity?.setShadowVisibility(true)
            } else {
                activity?.setShadowVisibility(false)
            }
        }
        refresh()
    }

    private fun refresh() {
        mHomeSwipeRefresh.isRefreshing = true
        mCollectionViewModel.getCollectReposByUser(ClientApplication.USERNAME)
    }
}