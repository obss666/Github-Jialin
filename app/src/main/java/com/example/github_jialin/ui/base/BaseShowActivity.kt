package com.example.github_jialin.ui.base

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.github_jialin.R
import com.example.github_jialin.viewmodel.ShowViewModel

abstract class BaseShowActivity : AppCompatActivity() {

    protected val viewModel by lazy { ViewModelProvider(this).get(ShowViewModel::class.java) }

    protected lateinit var topBar: RelativeLayout
    protected lateinit var backButton: Button
    protected lateinit var titleTextView: TextView
    protected lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

        topBar = findViewById(R.id.top_bar)
        backButton = findViewById(R.id.activityBackButton)
        titleTextView = findViewById(R.id.titleTextView)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)

        backButton.setOnClickListener {
            finish()
        }

        loadingProgressBar.visibility = ProgressBar.VISIBLE
    }

    protected abstract fun getLayoutResId(): Int

    protected fun setTitle(title: String) {
        titleTextView.text = title
    }

}