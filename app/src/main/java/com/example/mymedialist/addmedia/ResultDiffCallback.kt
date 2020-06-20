package com.example.mymedialist.addmedia

import androidx.recyclerview.widget.DiffUtil
import com.example.mymedialist.network.Result

class ResultDiffCallback: DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}