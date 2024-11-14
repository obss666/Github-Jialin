package com.example.github_jialin.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_jialin.R
import com.example.github_jialin.data.model.RepoInfo
import com.example.github_jialin.ui.activity.h5activity.RepoDetailActivity
import de.hdodenhof.circleimageview.CircleImageView

class RepoAdapter(private val activity: Activity,
    private val repoList: MutableList<RepoInfo>)
    : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repoName: TextView = view.findViewById(R.id.repoName)
        val repoDescription: TextView = view.findViewById(R.id.repoDescription)
        val repoLanguage: TextView = view.findViewById(R.id.repoLanguage)
        val repoStars: TextView = view.findViewById(R.id.repoStars)
        val repoAvatar: CircleImageView = view.findViewById(R.id.avatarItemImageView)
        val repoOwner: TextView = view.findViewById(R.id.loginNameItemText)
        var repoUrl : String = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            if(holder.repoUrl.isNotEmpty()) {
                val intent = Intent(activity, RepoDetailActivity::class.java)
                intent.putExtra("url", holder.repoUrl)
                activity.startActivity(intent)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repoList[position]
        holder.repoName.text = repo.name
        holder.repoDescription.text = repo.description
        holder.repoLanguage.text = repo.language
        holder.repoStars.text = repo.stars.toString()
        Glide.with(activity).load(repo.avatarUrl).into(holder.repoAvatar)
        holder.repoOwner.text = repo.login
        holder.repoUrl = repo.url!!
    }

    override fun getItemCount() = repoList.size

    fun setRepoList(repoList: List<RepoInfo>) {
        this.repoList.clear()
        this.repoList.addAll(repoList)
        notifyDataSetChanged()
    }
}

