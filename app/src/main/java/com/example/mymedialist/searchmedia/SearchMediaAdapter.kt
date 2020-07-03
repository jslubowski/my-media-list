package com.example.mymedialist.searchmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymedialist.R
import com.example.mymedialist.databinding.ApiMovieItemBinding
import com.example.mymedialist.network.Result
import timber.log.Timber

class SearchMediaAdapter(private val onClickListener: OnClickListener) : ListAdapter<Result, SearchMediaAdapter.ViewHolder>(ResultDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.clickListener(item)
        }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (movieEntity: Result) -> Unit) {
        fun onClick(movieEntity: Result) = clickListener(movieEntity)
    }

    class ViewHolder private constructor(val binding: ApiMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Result
        ) {
            if (item.posterPath != null) {
                Glide
                    .with(binding.movieCover.context)
                    .load("https://image.tmdb.org/t/p/w500/" + item.posterPath)
                    .into(binding.movieCover)
            }
            else {
                Timber.i("Cover for movie: ${item.originalTitle} was not found.")
                binding.movieCover.setImageResource(R.drawable.baseline_block_black_18dp)
            }
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