package com.example.github_jialin.ui.adapter

import android.app.Activity
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.github_jialin.data.model.RepoInfo
import java.util.Collections

class UnselectedAdapter(val activity: Activity, repoList: MutableList<RepoInfo>):
    EasyRepoAdapter(activity, repoList) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.dragArea.visibility = FrameLayout.VISIBLE
        holder.selectedImageView.visibility = ImageView.VISIBLE
    }
}