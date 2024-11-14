package com.example.github_jialin.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainFragmentPagerAdapter(fragmentActivity: FragmentActivity, fragmentList: List<Fragment>)
    : FragmentStateAdapter(fragmentActivity) {

    private val mFragmentList: List<Fragment> = fragmentList

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }
}
