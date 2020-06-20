package com.example.mymedialist.addmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymedialist.databinding.ApiMovieItemBinding
import com.example.mymedialist.network.Result

class AddMediaAdapter : ListAdapter<Result, AddMediaAdapter.ViewHolder>(ResultDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ApiMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Result
        ) {
            val res = itemView.resources

            binding.movieTitle.text = item.originalTitle // TODO truncate
            binding.movieYearText.text = item.releaseDate
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ApiMovieItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}