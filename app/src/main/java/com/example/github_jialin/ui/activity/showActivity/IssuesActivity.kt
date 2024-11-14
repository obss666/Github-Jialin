package com.example.github_jialin.ui.activity.showActivity

import android.os.Bundle
import android.widget.ProgressBar
import com.example.github_jialin.R
import com.example.github_jialin.ui.base.BaseShowActivity

class IssuesActivity : BaseShowActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_issues

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("议题")

        loadingProgressBar.visibility = ProgressBar.GONE
    }
}