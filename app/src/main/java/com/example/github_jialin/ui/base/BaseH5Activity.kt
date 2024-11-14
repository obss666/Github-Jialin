package com.example.github_jialin.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.github_jialin.R

abstract class BaseH5Activity : AppCompatActivity() {

    protected lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        val url = intent.getStringExtra("url")
        if (url != null) webView.loadUrl(url)
    }

    protected abstract fun getLayoutResId(): Int
}