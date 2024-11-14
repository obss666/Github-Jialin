package com.example.github_jialin.ui.activity.showActivity

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.github_jialin.R
import com.example.github_jialin.ui.base.BaseShowActivity

class DiscussActivity : BaseShowActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_discuss
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("讨论")

        loadingProgressBar.visibility = ProgressBar.GONE
    }
}