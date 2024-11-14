package com.example.github_jialin.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_jialin.R
import com.example.github_jialin.data.model.SearchUser
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val activity: Activity,
                   private val userList: MutableList<SearchUser>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: CircleImageView = view.findViewById(R.id.avatarItemImageView)
//        val name: TextView = view.findViewById(R.id.nameItemText)
        val loginName: TextView = view.findViewById(R.id.loginNameItemText)
//        val bio: TextView = view.findViewById(R.id.bioTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
//            if(holder.repoUrl.isNotEmpty()) {
//                val intent = Intent(activity, RepoDetailActivity::class.java)
//                intent.putExtra("url", holder.repoUrl)
//                activity.startActivity(intent)
//            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        Glide.with(activity).load(user.avatar_url).into(holder.avatar)
        holder.loginName.text = user.login
    }

    override fun getItemCount() = userList.size

    fun setUserList(userList: List<SearchUser>) {
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }
}