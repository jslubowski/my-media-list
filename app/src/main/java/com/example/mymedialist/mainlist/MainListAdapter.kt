package com.example.mymedialist.mainlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymedialist.databinding.ListItemBinding
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.util.smartTruncate

class MainListAdapter(private val onLongClickListener: OnLongClickListener) : ListAdapter<MovieEntity, MainListAdapter.ViewHolder>(MovieEntityDiffCallback()) {

    //TODO add onClickListener to navigate to dialog with rating and seenOn date

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnLongClickListener {
            onLongClickListener.onLongClick(item)
        }
        holder.bind(item)
    }

    class OnLongClickListener(val clickListener: (movieEntity: MovieEntity) -> Boolean) {
        fun onLongClick(movieEntity: MovieEntity) = clickListener(movieEntity)
    }

    class ViewHolder private constructor(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: MovieEntity
        ) {
            if (item.imageUrl != null) {
                Glide
                    .with(binding.movieCover.context)
                    .load("https://image.tmdb.org/t/p/w500/" + item.imageUrl)
                    .into(binding.movieCover)
            }

            binding.movieTitle.text = item.title.smartTruncate(23)
            binding.dateText.text = item.seenOnDate.toString()
            binding.descriptionText.text = item.description?.smartTruncate(100)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}