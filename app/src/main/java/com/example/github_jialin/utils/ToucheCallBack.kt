package com.example.github_jialin.utils

import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.github_jialin.ui.adapter.EasyRepoAdapter


class ToucheCallBack(private val adapter: EasyRepoAdapter) : ItemTouchHelper.Callback() {
    //声明不同转台下的移动方向
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipedFlags = 0
        return makeMovementFlags(dragFlags, swipedFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.movedRepo(viewHolder.layoutPosition, target.layoutPosition)
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.removeRepo(viewHolder.layoutPosition)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        //不是空闲状态--背景加深
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder!!.itemView.setBackgroundColor(Color.GRAY)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.setBackgroundColor(0)
    }
}