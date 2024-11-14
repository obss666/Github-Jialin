package com.example.github_jialin.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_jialin.R
import com.example.github_jialin.data.model.RepoInfo
import com.example.github_jialin.ui.activity.h5activity.RepoDetailActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Collections

open class EasyRepoAdapter(private val activity: Activity,
                      private val repoList: MutableList<RepoInfo>)
    : RecyclerView.Adapter<EasyRepoAdapter.ViewHolder>() {

    private var onItemClick: ((Int) -> Unit)? = null

    init {
        onItemClick = { position ->
            val repo = repoList[position]
            if (!repo.url.isNullOrEmpty()) {
                val intent = Intent(activity, RepoDetailActivity::class.java)
                intent.putExtra("url", repo.url)
                activity.startActivity(intent)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repoName: TextView = view.findViewById(R.id.repoNameEasy)
        val repoAvatar: CircleImageView = view.findViewById(R.id.avatarItemImageViewEasy)
        val repoOwner: TextView = view.findViewById(R.id.loginNameItemTextEasy)
        var repoUrl: String = ""

        val unselectedImageView: ImageView = view.findViewById(R.id.unselectedImageView)
        val dragArea: View = view.findViewById(R.id.dragArea)
        val selectedImageView: ImageView = view.findViewById(R.id.selectedImageView)
        val moveImageView: ImageView = view.findViewById(R.id.moveImageView)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo_easy, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repoList[position]
        holder.repoName.text = repo.name
        Glide.with(activity).load(repo.avatarUrl).into(holder.repoAvatar)
        holder.repoOwner.text = repo.login
        holder.repoUrl = repo.url!!
    }

    override fun getItemCount() = repoList.size

    fun getRepoList(): List<RepoInfo> {
        return repoList
    }

    fun getRepoInfo(position: Int): RepoInfo {
        return repoList[position]
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClick = listener
    }

    fun setRepoList(reposList: List<RepoInfo>) {
        this.repoList.clear()
        this.repoList.addAll(reposList)
        notifyDataSetChanged()
    }

    fun addRepo(repo: RepoInfo) {
        this.repoList.add(repo)
        notifyItemInserted(this.repoList.size - 1)
    }

    fun removeRepo(position: Int) {
        this.repoList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun movedRepo(oldPosition: Int, newPosition: Int) {
        Collections.swap(repoList, oldPosition, newPosition)
        notifyItemMoved(oldPosition, newPosition)
    }
}

