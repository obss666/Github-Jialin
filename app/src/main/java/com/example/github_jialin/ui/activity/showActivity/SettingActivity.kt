package com.example.github_jialin.ui.activity.showActivity

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import com.example.github_jialin.R
import com.example.github_jialin.ui.activity.LoginActivity
import com.example.github_jialin.ui.base.BaseShowActivity

class SettingActivity : BaseShowActivity() {


    private lateinit var signOutCardView: CardView

    override fun getLayoutResId(): Int {
        return R.layout.activity_setting
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("设置")

        loadingProgressBar.visibility = ProgressBar.GONE

        signOutCardView = findViewById(R.id.signOutCardView)

        signOutCardView.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("isSignOut", true)
            startActivity(intent)
            finish()
        }

    }
}