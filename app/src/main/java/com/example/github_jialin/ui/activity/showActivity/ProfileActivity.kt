package com.example.github_jialin.ui.activity.showActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import com.example.github_jialin.ClientApplication
import com.example.github_jialin.R
import com.example.github_jialin.ui.base.BaseShowActivity
import com.example.github_jialin.utils.showToast


class ProfileActivity : BaseShowActivity() {

    companion object {
        const val TAG = "YourProfileActivity"
    }

    private lateinit var mNameValueTextView: TextView
    private lateinit var mBioValueTextView: TextView
    private lateinit var mLocationValueTextView: TextView
    private lateinit var mBlogValueTextView: TextView
    private lateinit var mCompanyValueTextView: TextView
    private lateinit var mEmailValueTextView: TextView

    override fun getLayoutResId(): Int {
        return R.layout.activity_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle("资料")
        mNameValueTextView = findViewById(R.id.nameValueTextView)
        mBioValueTextView = findViewById(R.id.bioValueTextView)
        mLocationValueTextView = findViewById(R.id.locationValueTextView)
        mBlogValueTextView = findViewById(R.id.blogValueTextView)
        mCompanyValueTextView = findViewById(R.id.companyValueTextView)
        mEmailValueTextView = findViewById(R.id.emailValueTextView)


        mBlogValueTextView.setOnClickListener {
            val uri = Uri.parse(mBlogValueTextView.text.toString())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        viewModel.userLiveData.observe(this) { result ->
            val user = result.getOrNull()
            if (user != null) {
                val userInfo = user.toUserInfo()
                loadingProgressBar.visibility = ProgressBar.GONE
                mNameValueTextView.text = userInfo.name
                mBioValueTextView.text = userInfo.bio
                mLocationValueTextView.text = userInfo.location
                mBlogValueTextView.text = userInfo.blog
                mCompanyValueTextView.text = userInfo.company
                mEmailValueTextView.text = userInfo.email
                if(userInfo.blog!="unknown") {
                    mBlogValueTextView.setTextColor(resources.getColor(R.color.blue))
                }
            } else {
                "用户信息获取失败".showToast(this)
                result.exceptionOrNull()?.printStackTrace()
            }
        }
        viewModel.refresh(ClientApplication.USERNAME, 0)
    }
}