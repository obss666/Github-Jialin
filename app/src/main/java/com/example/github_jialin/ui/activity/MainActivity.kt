package com.example.github_jialin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.github_jialin.ClientApplication
import com.example.github_jialin.R
import com.example.github_jialin.ui.activity.showActivity.MyRepositoriesActivity
import com.example.github_jialin.ui.activity.showActivity.ProfileActivity
import com.example.github_jialin.ui.activity.h5activity.ProjectsActivity
import com.example.github_jialin.ui.activity.showActivity.SettingActivity
import com.example.github_jialin.ui.activity.showActivity.StarsActivity
import com.example.github_jialin.ui.adapter.MainFragmentPagerAdapter
import com.example.github_jialin.ui.fragment.HomeFragment
import com.example.github_jialin.ui.fragment.NotificationFragment
import com.example.github_jialin.ui.fragment.ExploreFragment
import com.example.github_jialin.utils.showToast
import com.example.github_jialin.viewmodel.ShowViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity() {

    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager2: ViewPager2
    private lateinit var mToolbar: Toolbar
    private lateinit var shadowLine: View
    private lateinit var mLoginName: TextView
    private lateinit var mAvatarImage: CircleImageView
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private lateinit var mAvatarImageViewNav: CircleImageView
    private lateinit var mIdTextNav: TextView
    private lateinit var mLoginTextNav: TextView
    private lateinit var mFollowTextNav: TextView

    private val mViewModel by lazy { ViewModelProvider(this).get(ShowViewModel::class.java) }

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbar = findViewById(R.id.mainToolbar)
        shadowLine = findViewById(R.id.shadow_line)
        mTabLayout = findViewById(R.id.mainTabLayout)
        mViewPager2 = findViewById(R.id.mainViewPager)
        mLoginName = findViewById(R.id.loginNameMainTitle)
        mAvatarImage = findViewById(R.id.avatarMainImageView)
        mDrawerLayout = findViewById(R.id.main)
        mNavigationView = findViewById(R.id.navView)
        mAvatarImageViewNav = mNavigationView.getHeaderView(0).findViewById(R.id.avatarImageViewNav)
        mIdTextNav = mNavigationView.getHeaderView(0).findViewById(R.id.idTextNav)
        mLoginTextNav = mNavigationView.getHeaderView(0).findViewById(R.id.loginTextNav)
        mFollowTextNav = mNavigationView.getHeaderView(0).findViewById(R.id.followTextNav)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mAvatarImage.setOnClickListener {
            mDrawerLayout.openDrawer(GravityCompat.START)
        }

        mToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.moreInMenu -> {
                    "clicked more".showToast(this)
                    true
                }

                R.id.searchInMenu -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }

                else -> {
                    false
                }
            }
        }

        val adapter = MainFragmentPagerAdapter(
            this,
            listOf(HomeFragment(), NotificationFragment(), ExploreFragment())
        )
        mViewPager2.adapter = adapter

        TabLayoutMediator(mTabLayout, mViewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.Home)
                    tab.icon = AppCompatResources.getDrawable(this, R.drawable.ic_home)
                }

                1 -> {
                    tab.text = getString(R.string.Notifications)
                    tab.icon = AppCompatResources.getDrawable(this, R.drawable.ic_notification)
                }

                2 -> {
                    tab.text = getString(R.string.Explore)
                    tab.icon = AppCompatResources.getDrawable(this, R.drawable.ic_explore)
                }
            }
        }.attach()

        mNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile->{
                    startActivity(Intent(this, ProfileActivity::class.java))
                }

                R.id.repositories -> {
                    startActivity(Intent(this, MyRepositoriesActivity::class.java))
                }

                R.id.projects -> {
                    var intent = Intent(this, ProjectsActivity::class.java)
                    intent.putExtra("url", "https://github.com/${ClientApplication.USERNAME}?tab=projects");
                    startActivity(intent)
                }

                R.id.stars -> {
                    startActivity(Intent(this, StarsActivity::class.java))
                }

                R.id.settings -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                }
            }
//            mDrawerLayout.closeDrawers()
            true
        }

        mViewModel.userLiveData.observe(this) { result ->
            val user = result.getOrNull()
            if (user != null) {
                val userInfo = user.toUserInfo()
                mLoginName.text = userInfo.login
                mLoginTextNav.text = userInfo.login
                mIdTextNav.text = "id: " + userInfo.id.toString()
                mFollowTextNav.text = "${userInfo.followers}个关注者・${userInfo.following}关注"

                Glide.with(this).load(user.avatar_url).into(mAvatarImage)
                Glide.with(this).load(user.avatar_url).into(mAvatarImageViewNav)

            } else {
                "failed to load user".showToast(this)
                result.exceptionOrNull()?.printStackTrace()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        mViewModel.refresh(ClientApplication.USERNAME, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_toolbar, menu)
        return true
    }

    fun setShadowVisibility(visible: Boolean) {
        if (visible) {
            shadowLine.visibility = View.VISIBLE
        } else {
            shadowLine.visibility = View.GONE
        }
    }
}