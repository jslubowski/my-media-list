package com.example.mymedialist.mainlist

import androidx.recyclerview.widget.DiffUtil
import com.example.mymedialist.model.MovieEntity

class MovieEntityDiffCallback: DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }
}