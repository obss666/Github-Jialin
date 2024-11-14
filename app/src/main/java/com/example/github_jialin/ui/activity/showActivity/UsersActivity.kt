package com.example.github_jialin.ui.activity.showActivity

import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github_jialin.R
import com.example.github_jialin.ui.adapter.UserAdapter
import com.example.github_jialin.ui.base.BaseShowActivity
import com.example.github_jialin.utils.LogUtil
import com.example.github_jialin.utils.showToast

class UsersActivity : BaseShowActivity() {

    companion object{
        const val TAG = "UsersActivity"
    }

    private lateinit var content: String
    private lateinit var showRecyclerView: RecyclerView

    override fun getLayoutResId(): Int {
        return R.layout.activity_users
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        content = intent.getStringExtra("content")?:""
        setTitle("人员")

        showRecyclerView = findViewById(R.id.showRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        val adapter = UserAdapter(this, mutableListOf())
        showRecyclerView.layoutManager = layoutManager
        showRecyclerView.adapter = adapter

        viewModel.userSearchLiveData.observe(this) { result->
            val list = result.getOrNull()
            if( list != null) {
                val userList = list.items
                LogUtil.d(TAG, userList.size.toString())
                loadingProgressBar.visibility = ProgressBar.GONE
                adapter.setUserList(userList)
            } else {
                "人员信息获取失败".showToast(this)
                result.exceptionOrNull()?.printStackTrace()
            }
        }

        viewModel.refresh(content,4)
    }
}